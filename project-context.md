# JavBlog Project Context

## Project Overview

**JavBlog** is a Spring Boot 1.5.4 web application for a blogging platform with user authentication, article management, messaging, and Facebook integration. The application uses MySQL for persistence and includes features for article creation, commenting, user profiles, and inter-user messaging.

**Technology Stack:**
- Java 8
- Spring Boot 1.5.4.RELEASE (parent POM)
- Spring Security with role-based access control
- Spring Data JPA with Hibernate
- MySQL 5.7
- JSP/JSTL for view rendering
- Log4j for logging
- Maven build system

---

## Java/Spring Conventions & Patterns

### 1. Package Organization

The project follows a layered architecture pattern:

```
src/main/java/pl/blog/
├── BlogApplication.java           # Main entry point
├── annotations/                   # Custom annotations
├── config/                        # Spring configuration classes
│   ├── AppConfig.java
│   ├── SecurityConfiguration.java
│   ├── DataSourceConfig.java
│   ├── MvcConfig.java
│   ├── AsyncConfig.java
│   ├── RabbitConfig.java
│   ├── async/                     # Async-specific configs
│   └── facebook/                  # Facebook integration configs
├── controllers/                   # Spring MVC controllers
│   ├── ArticleController.java
│   ├── ProfileController.java
│   ├── LoginController.java
│   ├── RegisterController.java
│   ├── MessageController.java
│   └── MainController.java
├── domain/                        # JPA entity classes
│   ├── Article.java
│   ├── Users.java
│   ├── Comment.java
│   ├── CommentComments.java
│   ├── Message.java
│   ├── Role.java
│   ├── CustomUserDetails.java
│   ├── PersistenceLogins.java
│   ├── UserConnection.java
│   ├── BasicArticle.java
│   └── io/                        # Domain interfaces (IO contracts)
├── dto/                           # Data Transfer Objects
│   ├── ArticleDTO.java
│   ├── UserDTO.java
│   ├── MessageDTO.java
│   ├── SearchArticleDTO.java
│   ├── SortDTO.java
│   ├── CommentCommentsDTO.java
│   ├── FacebookDTO.java
│   └── profileEdits/              # DTOs for profile operations
├── services/                      # Service layer
│   ├── ArticleServices.java
│   ├── UserServicesImpl.java
│   ├── MessageServicesImpl.java
│   ├── SessionServices.java
│   ├── AsyncServices.java
│   ├── ProfileServices.java
│   ├── RegistrationService.java
│   ├── UserConnectionServices.java
│   ├── components/                # Service-related components
│   ├── user/                      # User-specific services
│   │   └── security/              # Security-related user services
│   └── io/                        # Service I/O interfaces
├── repos/                         # Spring Data JPA repositories
│   ├── ArticleRepo.java
│   ├── CommentsRepo.java
│   ├── CommentCommentsRepo.java
│   ├── UserRepo.java
│   ├── MessageRepo.java
│   ├── RoleRepo.java
│   ├── BasicArticleRepo.java
│   └── UserConnectionRepo.java
├── converters/                    # Data conversion utilities
│   └── TextToHTML.java
├── interceptors/                  # HTTP interceptors
├── listeners/                     # Event listeners
└── annotations/                   # Custom annotations
```

---

### 2. Class Naming Conventions

| Component Type | Pattern | Examples |
|---|---|---|
| **Entity/Domain Models** | `[Noun]` (PascalCase) | `Article`, `Users`, `Comment`, `Message`, `Role` |
| **Controllers** | `[Noun]Controller` | `ArticleController`, `ProfileController`, `LoginController` |
| **Services** | `[Noun]Services` or `[Noun]ServicesImpl` | `ArticleServices`, `UserServicesImpl`, `MessageServicesImpl` |
| **Repositories** | `[Noun]Repo` | `ArticleRepo`, `CommentsRepo`, `UserRepo` |
| **DTOs** | `[Noun]DTO` | `ArticleDTO`, `UserDTO`, `MessageDTO` |
| **Utility Classes** | `[Action][Noun]` | `TextToHTML`, `CustomUserDetailsService` |
| **Configuration Classes** | `[Description]Config` or `[Description]Configuration` | `SecurityConfiguration`, `MvcConfig`, `DataSourceConfig` |

**Important:** The plural form "Users" is used for the main user entity (not "User"), establishing a specific precedent for this project.

---

### 3. Dependency Injection Pattern

**Constructor Injection is the REQUIRED pattern** (enforced by recent commit: "changed field injection to injection by constructor"):

✅ **CORRECT:**
```java
@Controller
public class ArticleController {
    private UserServices userServices;
    private ArticleServices services;

    @Autowired
    public ArticleController(UserServices userServices, ArticleServices services) {
        this.userServices = userServices;
        this.services = services;
    }
}
```

❌ **INCORRECT (field injection):**
```java
@Controller
public class ArticleController {
    @Autowired
    private UserServices userServices;
}
```

**Why:** Field injection is brittle and makes testing harder. Constructor injection is:
- More testable (easier to inject test doubles)
- Explicit about dependencies
- Enables immutability of fields
- Prevents NullPointerException at runtime

---

### 4. Controller Patterns

#### Request Mapping
- Use `@GetMapping` and `@PostMapping` instead of `@RequestMapping(method=...)`
- Path variables: `@PathVariable("id") Long id`
- Query parameters: `@RequestParam(value = "sort", required = false, defaultValue = "0") Integer sort`
- Request body: `@ModelAttribute("dto") SomeDTO dto`

#### Example Pattern:
```java
@Controller
public class ArticleController {

    @ModelAttribute("cat")
    public String[] category() {
        return services.getLabels();  // Makes model attribute available to all methods
    }

    @GetMapping("/articles")
    public String getArticles(Model model, HttpServletRequest request, HttpSession session,
                              @RequestParam(value = "sort", required = false, defaultValue = "0") Integer sort) {
        // Logic here
        return "viewName";  // Returns view template name (JSP file without extension)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/article/{id}")
    public String getArticleById(Model model, @PathVariable("id") Long id) {
        // Only accessible to ROLE_USER
        return "article";
    }
}
```

#### Key Patterns:
- Use `Model` object to pass data to views
- Use `HttpSession` for session-scoped data
- Use `@PreAuthorize` annotations for method-level security (enabled in `BlogApplication.java`)
- Return string view names (JSP files resolve automatically via `application.properties` configuration)
- Use DTOs to accept form data (`@ModelAttribute`)

---

### 5. Service Layer Patterns

#### Class Structure:
```java
@Service
@Transactional  // CRITICAL: All service methods are transactional by default
public class ArticleServices {

    private ArticleRepo articleRepo;
    private CommentsRepo commentsRepo;
    private UserServicesImpl userServices;

    @Autowired
    public ArticleServices(ArticleRepo articleRepo, CommentsRepo commentsRepo, UserServicesImpl userServices) {
        this.articleRepo = articleRepo;
        this.commentsRepo = commentsRepo;
        this.userServices = userServices;
    }

    // IMPORTANT: Also include a no-arg constructor for Spring
    public ArticleServices() {
    }

    // Service methods follow naming patterns:
    public List<Article> getAll()                    // Getter
    public List<Article> mostVisited()               // Specific query method
    public void saveOrUpdate(Article article)        // Save/update operation
    public void deleteArticle(Long id)               // Delete operation
    public Map<String, String> failures(ArticleDTO)  // Validation helper
}
```

#### Critical Rules:
1. **Always include both parameterized AND no-arg constructors** in service classes
2. **All services are `@Transactional`** at class level — this means:
   - All methods automatically participate in a database transaction
   - No need to add `@Transactional` to individual methods
   - Lazy-loaded collections work within service methods
   - Changes to entities are automatically persisted at method end
3. **Use `@Autowired` on constructor** with all required dependencies
4. **Service method names should be descriptive:**
   - `getXxx()` for retrievals
   - `saveOrUpdate()` for persistence
   - `deleteXxx()` for deletions
   - `xxxCounter()` for increment operations
   - `failures()` for validation logic

---

### 6. Domain Model (Entity) Patterns

#### JPA Configuration:
```java
@Entity
@Table(name = "user")  // Explicitly define table name when different from class name
public class Users implements Serializable {

    @Id
    @GeneratedValue  // Auto-generate ID (defaults to IDENTITY strategy)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    // Relationships with cascade and fetch strategies
    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
```

#### Key Rules:
1. **All entities implement `Serializable`** (enables session storage)
2. **Collections initialize with concrete types:** `new ArrayList<>()` or `new HashSet<>()`
3. **Use `@GeneratedValue` for auto-incrementing IDs** (no strategy specified = use dialect default)
4. **Relationships define cascade and fetch strategies:**
   - `FetchType.EAGER` for frequently-accessed relationships
   - `CascadeType.ALL` for dependent entities
   - `orphanRemoval = true` for 1-to-many when child should be deleted if parent is deleted
5. **Use `@Table(name = ...)` when table name differs from class name**
6. **Getters/Setters are REQUIRED** for all fields (no Lombok — this is legacy Spring Boot 1.5 era code)
7. **Include constructors:**
   - No-arg constructor (required by Hibernate)
   - Constructor accepting relevant fields
   - Constructor accepting another entity instance (for copying)

---

### 7. DTO Patterns

```java
public class ArticleDTO implements Serializable {

    @NotEmpty
    @NotNull
    @Size(min = 6, max = 40)
    private String title;

    @NotEmpty
    @NotNull
    @Min(40)  // Note: @Min applied to String (checks string length)
    private String body;

    @NotNull
    @NotEmpty
    private String[] category;

    private String imgUrl;

    // Getters and setters for all fields
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
```

#### Validation Annotations:
- `@NotEmpty` - must not be null or empty
- `@NotNull` - must not be null
- `@Size(min=X, max=Y)` - string/collection size constraint
- `@Min(n)` - minimum value (when applied to String, checks length)
- `org.hibernate.validator.constraints.NotEmpty` (Hibernate-specific)

#### Key Rules:
1. All DTOs implement `Serializable`
2. DTOs mirror entity fields but add validation annotations
3. Use validation annotations from both `javax.validation.constraints.*` and `org.hibernate.validator.constraints.*`
4. Include getters/setters for all fields

---

## Security & Authentication Patterns

### SecurityConfiguration Structure

```java
@Configuration
@EnableWebSecurity
@ComponentScan("pl.blog")
public class SecurityConfiguration {

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/", "/resources*//**", "/css*//**", "/login", "/register").permitAll()
                    .antMatchers("/newarticle", "/profile").access("hasRole('ROLE_USER')")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureUrl("/login?error")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                .and()
                .csrf()
                    .csrfTokenRepository(csrfTokenRepository())
                .and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(604800);  // 1 week
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(11);  // Bcrypt strength 11
        }
    }
}
```

#### Key Patterns:
1. **Role-based access control:** Use `@PreAuthorize("hasRole('ROLE_USER')")` on controller methods
2. **Password encoding:** Use `BCryptPasswordEncoder(11)` (strength parameter = 11)
3. **CSRF protection:** Enabled via `csrfTokenRepository()`
4. **Remember-me:** Stored in database via `persistentTokenRepository()`, valid for 604800 seconds (1 week)
5. **Multiple security chains:** Use `@Order(1)` when defining multiple `WebSecurityConfigurerAdapter` implementations

---

## Database & Configuration

### application.properties
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/mojabaza?useSSL=false
spring.datasource.username=Mike
spring.datasource.password=1234
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
spring.jpa.hibernate.ddl-auto=create-drop  # DANGER: Recreates schema on startup

# Social Integration
spring.social.facebook.appId=760505277482869
spring.social.facebook.appSecret=4dfca96eb1392419280461a3e6d06022

# Server
server.port=8090
```

#### Important Notes:
- `ddl-auto=create-drop` is a development configuration — it **drops and recreates the database on every startup**
- Credentials are embedded in properties files (not a security best practice, but legacy pattern)
- MySQL 5 dialect is used
- Facebook credentials are configured for Spring Social

---

## Testing Patterns & Conventions

### Test File Organization

```
src/test/java/pl/blog/
├── BlogApplicationTests.java           # Integration test
├── domain/
│   ├── articles/
│   │   └── ArticleTest.java
│   └── user/
│       └── UserTest.java
└── services/
    ├── ArticleServicesTest.java
    ├── MessageServicesImplTest.java
    └── UserConnectionServicesTest.java
```

### Test Class Naming Convention

**Pattern:** `[ClassName]Test`

Examples:
- `ArticleTest`
- `UserTest`
- `ArticleServicesTest`
- `UserConnectionServicesTest`
- `BlogApplicationTests` (integration tests)

### Test Framework & Dependencies

- **Framework:** JUnit 4 (via Spring Boot 1.5.4 parent)
- **Spring Test Integration:** `@RunWith(SpringRunner.class)` and `@SpringBootTest`
- **JPA Testing:** `@DataJpaTest` for repository tests
- **Test Configuration:** Custom `@TestConfiguration` inner classes for test-specific beans
- **Assertions:** JUnit 4 `Assert` class (NOT AssertJ or Hamcrest)

### Test Method Naming Conventions

**Pattern:** Descriptive camelCase names describing what is being tested

Examples:
```java
public void setTags()                              // Simple action
public void emailComparison()                      // Feature being tested
public void countNewMessages()                     // Service operation
public void lastMessageWithContact()               // Specific query
public void listAllMessagesFromUserWithId()        // Detailed description
```

**Note:** Method names tend to be inconsistent in verbosity — some are simple (`setTags`) while others are very descriptive. Use descriptive names that clearly indicate what is being tested.

### Assertion Patterns

```java
@Test
public void someTest() {
    // Using assertTrue with comparison
    Assert.assertTrue(map.size() == 0);  // Instead of assertEquals(0, map.size())
    Assert.assertFalse(list.isEmpty());

    // Using assertion with message
    Assert.assertNotNull("MAP ARTICLE NULL", map);
    Assert.assertFalse("MAP ARTICLE EMPTY", map.isEmpty());

    // Direct equality
    Assert.assertEquals(12, list.size());
}
```

#### Key Patterns:
1. Use `Assert.assertTrue()` with comparison operators (not `assertEquals()`)
2. Always include descriptive message parameter in critical assertions
3. No Hamcrest matchers (`assertThat()`) or fluent assertions (AssertJ)

### Test Class Structure

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServicesTest {

    @TestConfiguration
    static class ArticleServicesTestContextConfiguration {
        @Bean
        public ArticleServices articleServices() {
            return new ArticleServices();
        }
    }

    @Autowired
    private ArticleServices articleServices;

    @Autowired
    private ArticleRepo articleRepo;

    @Test
    public void testSomeFeature() {
        // Test code here
        List<Article> articles = articleServices.getAll();
        Assert.assertTrue(articles.size() > 0);
    }
}
```

#### Key Patterns:
1. Use `@RunWith(SpringRunner.class)` and `@SpringBootTest` for integration tests
2. Create `@TestConfiguration` inner class to define test-specific beans
3. Inject services via `@Autowired`
4. Include both service and repository injections for testing persistence layer
5. Test methods start with `test` prefix

---

## View Layer (JSP/JSTL)

### View Rendering Convention

- Views are stored in `src/main/webapp/jsp/` (inferred from controller return strings)
- Return string from controller: `return "articles"` → resolves to `articles.jsp`
- Security tag library available: `<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>`

---

## Code Quality & Error Handling Rules

### Exception Handling Patterns

```java
try {
    services.articleVisitCounter(id);
    model.addAttribute("comments", services.getCommentsFromArticleId(id));
    model.addAttribute("article", TextToHTML.articleTexts(services.getArticleById(id)));
} catch (NullPointerException npe) {
    return "redirect:/articles";  // Fallback view
}
```

#### Key Rules:
1. **NullPointerException is caught and handled** — services may return null, so defensive null checking is expected
2. **Service calls should be wrapped in try-catch blocks** where null is a possibility
3. **Return to a safe view on exception** rather than throwing

### Validation Patterns

```java
Map<String, String> map = services.failures(articleDTO);  // Returns error map
if (map != null) {      // null if no errors
    model.addAttribute("faliure", map);  // Note: "faliure" typo is in actual code
    return "/addarticle";
}
```

#### Pattern:
- Validation is done in service layer
- Service method returns `null` if validation passes
- Service method returns `Map<String, String>` of errors if validation fails
- **Note:** There's a typo in the codebase: "faliure" instead of "failure" — this appears to be intentional/legacy

---

## Common Gotchas & Anti-Patterns to Avoid

1. **❌ Field Injection:** Do NOT use `@Autowired` on fields — use constructor injection
2. **❌ Missing No-Arg Constructor:** Service classes must have both parameterized AND no-arg constructors
3. **❌ Forgetting `@Transactional`:** Services MUST be `@Transactional` for lazy loading to work
4. **❌ String Concatenation in Queries:** Services use naive string concatenation for building query strings — avoid this in new code if possible
5. **❌ Magic String Constants:** Many hardcoded strings (URLs, parameter names) exist throughout controllers
6. **❌ Session Attribute Management:** Heavy reliance on HttpSession for stateful data — be careful about consistency

---

## Commit History & Evolution

**Recent commits indicate active maintenance:**
- Constructor injection migration (field injection → constructor injection)
- Security updates for profile operations
- Article edit/delete functionality from profile
- Bug fixes and cleanup

---

## Critical Implementation Rules for AI Agents

When generating code for this project, you MUST follow these rules:

1. **Always use constructor injection** — never use field injection
2. **Services MUST have `@Transactional` at class level** and both parameterized + no-arg constructors
3. **Controllers MUST use `@GetMapping`/`@PostMapping`** and constructor injection
4. **All entities MUST implement `Serializable`** and have no-arg constructors
5. **All DTOs MUST include validation annotations** from `javax.validation.constraints.*`
6. **Tests MUST follow naming pattern** `[ClassName]Test` with JUnit 4 assertions only
7. **Methods MUST use try-catch for NullPointerException** when calling services that may return null
8. **Role-based security MUST use `@PreAuthorize("hasRole('...')")`** on controller methods
9. **All fields in entities/DTOs MUST have getters and setters** (no Lombok)
10. **Initialize all collections with concrete types** (`new ArrayList<>()`, `new HashSet<>()`)

---

## Project-Specific Terminology

- **"faliure"** (typo intentional) — refers to validation error maps
- **"Repo"** — Spring Data JPA repository (not Repository)
- **"Services"** vs **"ServicesImpl"** — service implementations (mixed naming)
- **Model attributes** — data passed to JSP views via Spring's `Model` object
- **Categories** — article tags/labels for classification
