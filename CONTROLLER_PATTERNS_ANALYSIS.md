# Controller Patterns Analysis

## Overview
Analysis of coding patterns across three Spring MVC controllers in the JavBlog project:
- `ArticleController.java`
- `MainController.java`
- `ProfileController.java`

---

## 1. Class Structure Patterns

### Package Organization
All controllers reside in `pl.blog.controllers` package, following standard Spring project structure.

### Class-Level Annotations
```
@Controller
```
- All three controllers use the `@Controller` annotation for Spring MVC routing
- No `@RestController` usage (traditional view-based controllers, not REST APIs)

### Constructor-Based Dependency Injection
All controllers follow constructor injection pattern with `@Autowired`:

**ArticleController:**
```java
@Autowired
public ArticleController(UserServices userServices, ArticleServices services) {
    this.userServices = userServices;
    this.services = services;
}
```

**MainController:**
```java
@Autowired
public MainController(ArticleServices articleServices, Facebook facebook, 
                     ConnectionRepository connectionRepository) {
    this.articleServices = articleServices;
    this.facebook = facebook;
    this.connectionRepository = connectionRepository;
}
```

**ProfileController:**
```java
@Autowired
public ProfileController(ProfileServices profileServices) {
    this.profileServices = profileServices;
}
```

**Pattern:** Constructor injection is preferred over field injection (@Autowired on fields)

---

## 2. Method Naming Conventions

### GET Request Methods
- `getArticles()` - Retrieves list of articles
- `searchArticles()` - Handles search functionality
- `getArticleById()` - Retrieves single article by ID
- `getArticle()` - Retrieves article form for creation
- `mainPage()` - Main landing page
- `profile()` - User profile view
- `edit()` - Edit form display
- `noaccess()` - Access denied page
- `renderErrorPage()` - Error page rendering

**Pattern:** GET methods prefixed with `get`, `search`, or descriptive names; no CRUD operation prefixes

### POST Request Methods
- `postCommentByArticleId()` - Posts comments to articles
- `postArticle()` - Creates new article
- `postEdit()` - Handles profile edits

**Pattern:** POST methods prefixed with `post`

### Utility Methods
- `isCreator()` - Private boolean check for authorization

**Pattern:** Private helper methods for cross-cutting concerns

---

## 3. Annotation Patterns

### Request Mapping Annotations

#### @GetMapping
Used for all HTTP GET requests:
```java
@GetMapping("/articles")
@GetMapping("/article/{id}")
@GetMapping("/newarticle")
@GetMapping("/")
@GetMapping("/profile")
@GetMapping("/profile/edit/{edit}")
```

**Pattern:** Modern approach using `@GetMapping` instead of `@RequestMapping(method = GET)`

#### @PostMapping
Used for HTTP POST requests:
```java
@PostMapping("/article/{id}")
@PostMapping("/newarticle")
```

**Pattern:** Consistent use of `@PostMapping`

#### @RequestMapping
Used for legacy/multiple method handling:
```java
@RequestMapping("/noaccess")
@RequestMapping("/errors")
@RequestMapping("favicon.ico")

// Mixed methods (POST and DELETE)
@RequestMapping(value = "/profile/edit/{edit}", method = {RequestMethod.POST, RequestMethod.DELETE})
```

**Pattern:** Used for non-standard cases or fallback paths; occasionally supports multiple HTTP methods

### Security Annotations

#### @PreAuthorize
Used for role-based access control:
```java
@PreAuthorize("hasRole('ROLE_USER')")
public String searchArticles(...) { }

@PreAuthorize("hasRole('ROLE_USER')")
public String getArticleById(...) { }

@PreAuthorize("hasRole('ROLE_USER')")
public String profile(...) { }
```

**Pattern:** Applied at method level; restricts endpoints to authenticated users with ROLE_USER

### Method Parameter Annotations

#### @PathVariable
```java
@PathVariable("id") Long id
@PathVariable String edit
@PathVariable(value = "edit", required = false) String edit
```

**Pattern:** Used for extracting path parameters; sometimes marked as optional with `required = false`

#### @RequestParam
```java
@RequestParam(value = "sort", required = false, defaultValue = "0") Integer sort
@RequestParam(value = "search", required = false) String search
@RequestParam(value = "tag", required = false) String[] categories
@RequestParam(value = "page", required = false, defaultValue = "0") Integer page
@RequestParam(value = "id", required = false) Long id
@RequestParam(value = "edit", required = false) String edit
@RequestParam("nr") Integer nr
```

**Pattern:** 
- Always includes parameter name value
- Uses `required = false` for optional parameters
- Provides `defaultValue` for optional parameters with sensible defaults (0, empty string)
- Supports array types for multi-select (e.g., categories)

#### @ModelAttribute
```java
@ModelAttribute("article") ArticleDTO articleDTO
@ModelAttribute("comment") TextDTO textDTO
@ModelAttribute("sort") SortDTO sortDTO
@ModelAttribute("text") SearchArticleDTO searchArticleDTO
```

**Pattern:** 
- Binds form data to DTOs
- Specifies model attribute name for use in views
- Applied to parameter objects for form submission handling

### Model Attribute Initialization

#### @ModelAttribute (method level)
```java
@ModelAttribute("cat")
public String[] category() {
    return services.getLabels();
}
```

**Pattern:** 
- Method-level annotation to populate model with reference data
- Returns category labels for all views
- Makes data available to all view templates automatically

---

## 4. Request Mapping Conventions

### URL Structure
```
GET  /                           # Main page
GET  /articles                   # Article list
GET  /articles/search            # Search articles
GET  /article/{id}               # Single article view
GET  /newarticle                 # New article form
POST /newarticle                 # Create article
POST /article/{id}               # Post comment on article
GET  /profile                    # Profile view (own or by ?id=)
GET  /profile/edit/{edit}        # Edit form
POST /profile/edit/{edit}        # Save edits
GET  /noaccess                   # 403 error page
GET  /errors?nr=                 # Error page handler
```

**Patterns:**
- RESTful resource-based naming (`/article/{id}`, `/profile`)
- Consistent use of hyphens (no underscores)
- Path variables for dynamic IDs
- Query parameters for filtering/pagination (`?sort=`, `?search=`, `?page=`)
- Form operations use same path for GET (form) and POST (submission)

---

## 5. Return Types

### String Return Type
Most controllers return `String` (view name):
```java
public String getArticles(...) { return "articles"; }
public String getArticleById(...) { return "article"; }
public String postArticle(...) { return "redirect:/"; }
public String profile(...) { return "profile"; }
```

**Pattern:** Traditional Spring MVC view-based returns

### String Redirect
```java
return "redirect:/articles";
return "redirect:/profile#yourArticles";
return "redirect:/profile";
return "redirect:/logout";
return "redirect:/";
```

**Pattern:** 
- Uses `redirect:` prefix for post-redirect-get pattern
- Prevents form resubmission
- Sometimes includes URL fragments (`#yourArticles`)

### ModelAndView Return Type
```java
public ModelAndView renderErrorPage(...) {
    ModelAndView errorPage = new ModelAndView("403");
    errorPage.addObject("errorMsg", errorMsg);
    return errorPage;
}
```

**Pattern:** 
- Used when returning both model data and view explicitly
- Less common than String return
- Useful for error pages with dynamic content

---

## 6. Error Handling Patterns

### Try-Catch Pattern
```java
try {
    services.articleVisitCounter(id);
    model.addAttribute("comments", services.getCommentsFromArticleId(id));
    model.addAttribute("article", TextToHTML.articleTexts(services.getArticleById(id)));
    
    if (isCreator(request, id)) {
        // ... delete/edit logic
    }
} catch (NullPointerException npe) {
    return "redirect:/articles";
}
```

**Pattern:** 
- Catches `NullPointerException` when article not found
- Redirects to safe fallback page
- Prevents error page exposure

### Validation via Service Layer
```java
Map<String, String> map = services.failures(articleDTO);
if (map != null) {      // null if empty from errors
    model.addAttribute("faliure", map);
    return "/addarticle";
}
```

**Pattern:**
- Delegates validation to service layer
- Returns error map from service
- Re-renders form with error messages on validation failure
- Named attribute: `faliure` (typo: should be `failure`)

### Authorization Check
```java
if (isCreator(request, id)) {
    if (delete != null) {
        services.deleteArticle(id);
        return "redirect:/profile#yourArticles";
    }
    if (edit != null) {
        // ... edit logic
    }
}
```

**Pattern:**
- Manual authorization check using private helper method
- Leverages Spring Security's `@PreAuthorize` at method level
- User identity from `request.getUserPrincipal().getName()`

### Error Page Switch Statement
```java
switch (nr) {
    case 400: { errorMsg = "Http Error Code: 400. Bad Request"; break; }
    case 401: { errorMsg = "Http Error Code: 401. Unauthorized"; break; }
    case 404: { errorMsg = "Http Error Code: 404. Resource not found"; break; }
    case 500: { errorMsg = "Http Error Code: 500. Internal Server Error"; break; }
    default: { errorMsg = "Ups! Something is not ok"; }
}
errorPage.addObject("errorMsg", errorMsg);
```

**Pattern:**
- Maps HTTP error codes to user-friendly messages
- Centralizes error handling in MainController

---

## 7. Session and Request Handling Patterns

### HttpSession Usage
```java
session.setAttribute("articles", services.getArticlesWithoutWelcomePage());
session.setAttribute("sortNr", sort);
session.setAttribute("sortCache", "&search=&tag=");

// Retrieval
switch ((Integer) session.getAttribute("sortNr")) { }
```

**Pattern:**
- Stores filter/sort preferences in session
- Maintains state across requests
- Type casting required on retrieval

### HttpServletRequest Usage
```java
@GetMapping("/articles")
public String getArticles(Model model, HttpServletRequest request, ...) {
    model.addAttribute("queryString", request.getQueryString().split("&page")[0]);
}

// User principal
String username = request.getUserPrincipal().getName();
```

**Pattern:**
- Access to raw query strings
- User identity extraction via `getUserPrincipal()`

### HttpServletResponse
```java
public String getArticles(..., HttpServletResponse response, ...) {
    // Parameter passed but unused (commented out cookie logic)
}
```

**Pattern:** 
- Declared but underutilized
- Suggests cookie handling was planned but removed

### HttpSession Parameter
```java
@GetMapping("/articles")
public String searchArticles(Model model, HttpSession session, HttpServletResponse response, SessionStatus status, ...) {
}
```

**Pattern:**
- Direct session object injection
- More convenient than extracting from request

---

## 8. Model Population Patterns

### Consistent Model Attribute Names
```java
model.addAttribute("article", articleDTO);
model.addAttribute("articles", articleList);
model.addAttribute("comments", commentList);
model.addAttribute("sortedArticles", filteredList);
model.addAttribute("profile", user);
```

**Pattern:**
- Singular for single objects
- Plural for collections
- Lowercase first letter (standard Java naming)

### Attribute Re-initialization
```java
model.addAttribute("comment", new TextDTO());
model.addAttribute("commentComment", new CommentCommentsDTO());
```

**Pattern:**
- Fresh DTO instances created for forms
- Clears previous form data
- Prevents stale data in views

---

## 9. Code Quality Issues & Antipatterns

### ⚠️ Code Duplication
- `searchArticles()` and commented-out `postArticles()` contain identical logic
- String manipulation with StringBuilder appears twice
- String splitting logic repeated (`split("=")[2]`)

### ⚠️ Magic Numbers
```java
if (sort == 0) { }
switch ((Integer) session.getAttribute("sortNr")) {
    case 0: // Default sort
    case 1: // Most visited
    case 2: // Most commented
    case 3: // Newest
    case 4: // Oldest
}
```

**Pattern:** Magic numbers without constants; should use enum or constants

### ⚠️ Type Casting
```java
(Integer) session.getAttribute("sortNr")
(String[]) // implicit in categories parameter
```

**Pattern:** Session attributes require explicit casting; type safety issue

### ⚠️ Commented Code
- Entire POST method for articles commented out
- Cookie handling commented out
- Facebook integration commented out

**Pattern:** Should be removed or moved to feature branch

### ⚠️ String Array Splitting
```java
request.getQueryString().split("&page")[0]
session.getAttribute("sortCache").toString().split("=")[2].split("&")[0]
```

**Pattern:** Fragile string parsing; should use URL builder or existing libraries

### ⚠️ Typo in Variable Names
```java
model.addAttribute("faliure", map);  // Should be "failure"
String faliure = profileServices.faliure(...);  // Should be "failure"
```

---

## 10. Summary of Key Patterns

### Architecture
| Pattern | Usage | Frequency |
|---------|-------|-----------|
| Constructor Injection | All controllers | 3/3 |
| @Controller stereotype | All controllers | 3/3 |
| Service layer delegation | All methods | Consistent |
| Model attribute population | All GET methods | Consistent |

### Request Handling
| Pattern | Usage | Frequency |
|---------|-------|-----------|
| @GetMapping / @PostMapping | Modern endpoints | Most |
| @RequestMapping | Legacy/multiple methods | Fallback |
| Path variables | Dynamic IDs | Frequent |
| Query parameters | Filters/pagination | Frequent |
| @ModelAttribute | Form binding | Frequent |

### Security
| Pattern | Usage | Frequency |
|---------|-------|-----------|
| @PreAuthorize | Method-level | Moderate |
| Manual authorization checks | isCreator() | Selective |
| getUserPrincipal().getName() | User identity | Frequent |

### Response Handling
| Pattern | Usage | Frequency |
|---------|-------|-----------|
| String (view name) | View rendering | Most |
| redirect: | POST-redirect-GET | Frequent |
| ModelAndView | Complex responses | Rare |
| Try-catch | Error handling | Selective |

### Data Management
| Pattern | Usage | Frequency |
|---------|-------|-----------|
| Session attributes | State persistence | Frequent (ArticleController) |
| Form DTOs | Data transfer | Frequent |
| Service validation | Error checking | Moderate |
| Query building | Dynamic URLs | Moderate |

---

## 11. Best Practices Observed

✅ **Constructor Injection** - All dependencies injected via constructor  
✅ **Separation of Concerns** - Business logic delegated to services  
✅ **RESTful URLs** - Consistent resource-based URL structure  
✅ **Security Integration** - @PreAuthorize used for access control  
✅ **Model Population** - Views receive pre-populated model attributes  
✅ **Modern Annotations** - @GetMapping/@PostMapping used over @RequestMapping  

---

## 12. Recommendations for Improvement

### High Priority
1. **Remove Magic Numbers** - Create constants for sort options (SORT_DEFAULT, SORT_MOST_VISITED, etc.)
2. **Fix Type Safety** - Consider using enums for session attributes
3. **Remove Dead Code** - Delete commented-out methods and logic
4. **Fix String Parsing** - Use URL builders or URI components utilities
5. **Fix Typos** - Rename `faliure` → `failure` throughout

### Medium Priority
1. **Extract Duplicate Code** - Refactor searchArticles/postArticles logic to shared method
2. **Add Exception Handling** - More specific exception types instead of NullPointerException
3. **Use Constants** - Extract phlabels array to static final field
4. **Validation Framework** - Consider @Valid/@Validated for automatic form validation

### Low Priority
1. **Add Method Documentation** - JavaDoc for public methods
2. **Consistent Naming** - All methods follow get/post/set conventions
3. **Reduce Session Usage** - Consider URL parameters or database caching
4. **Type Safety** - Generic session attribute getters

---

## Conclusion

The controllers follow a traditional Spring MVC pattern with good separation of concerns through service layer delegation. The codebase demonstrates solid use of modern Spring annotations (@GetMapping, @PostMapping) and security integration (@PreAuthorize). However, code quality can be improved by removing duplication, eliminating magic numbers, and fixing type safety issues. The architecture is maintainable but would benefit from addressing the antipatterns identified above.
