package pl.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.blog.converters.TextToHTML;
import pl.blog.domain.*;
import pl.blog.dto.ArticleDTO;
import pl.blog.repos.ArticleRepo;
import pl.blog.repos.BasicArticleRepo;
import pl.blog.repos.CommentCommentsRepo;
import pl.blog.repos.CommentsRepo;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Mike on 19.07.2017.
 */
@Service
@Transactional
public class ArticleServices {

    private ArticleRepo articleRepo;
    private CommentsRepo commentsRepo;
    private CommentCommentsRepo commentCommentsRepo;
    private UserServicesImpl userServices;
    private BasicArticleRepo basicArticleRepo;

    @Autowired
    public ArticleServices(ArticleRepo articleRepo, CommentsRepo commentsRepo, CommentCommentsRepo commentCommentsRepo, UserServicesImpl userServices, BasicArticleRepo basicArticleRepo) {
        this.articleRepo = articleRepo;
        this.commentsRepo = commentsRepo;
        this.commentCommentsRepo = commentCommentsRepo;
        this.userServices = userServices;
        this.basicArticleRepo = basicArticleRepo;
    }


    public ArticleServices() {
    }

    public final String[] labels = {           // labels to checkboxes
            "JavaEE",
            "Java",
            "Spring",
            "Boot",
            "Security",
            "Mvc",
            "Hibernate",
            "News",
            "Other"
    };

    public final String[] sortOptions = {
            "Most visited",
            "Most commented",
            "Newest",
            "Oldest"
    };

    public List<Article> mostVisited() {
        return articleRepo.findAllByOrderByVisitCounterDesc();
    }

    public List<Article> mostCommented() {
        return articleRepo.findAllByOrderByCommentsCounterDesc();
    }

    public List<Article> newestArticles() {
        return articleRepo.findAllByOrderByDateDesc();
    }

    public List<Article> oldestArticles() {
        return articleRepo.findAllByOrderByDateAsc();
    }

    public List<Article> top4newestArticles() {     // for main site
        return articleRepo.findTop4ByOrderByDateDesc();
    }

    // with this func we get list of first 50 letters from articles what we want
    public Map<Long, String> articlesFirstLetters(List<Article> articles) {
        Map<Long, String> stringMap = new HashMap<>();
        for (Article article : articles) {
            stringMap.put(article.getId(), TextToHTML.stringBuilder(getFirstBodyLetters(article.getId()), 18));
        }
        return stringMap;
    }

    public void deleteArticle(Long id) {
        articleRepo.delete(id);

    }

    public void deleteCommentsFromArticleId(Long id) {
        commentsRepo.delete(getArticleById(id).getComments());
    }


    public void articleVisitCounter(Long id) {
        Article article = getArticleById(id);
        try {
            article.setVisitCounter(article.getVisitCounter() + 1);
        } catch (NullPointerException npe) {
            return;
        }

        articleRepo.save(article);
    }

    public List<Comment> getCommentsFromArticleId(Long id) {
        return commentsRepo.findByArticleIdOrderByDateDesc(id);
    }

    public void addCommentToComment(CommentComments comments, Long id) {
        Comment comment = commentsRepo.findOne(id);

        List<CommentComments> commentComments = comment.getComments();
        commentComments.add(comments);
        comment.setComments(commentComments);

        commentCommentsRepo.save(commentComments);
        commentsRepo.save(comment);

    }

    public List<Article> xRandomArticles(int x) {
        List<Article> articles = new ArrayList<>();
        List<Article> allArticles = getAll();

        for (int i = 0; i < x; i++) {
            int r = (int) (Math.random() * 1000) % allArticles.size();
            if (!articles.contains(allArticles.get(r))) {
                Article article = allArticles.get(r);

                article.setHeader(TextToHTML.stringBuilder(article.getHeader(), 15));
                articles.add(article);
            } else {
                i--;
            }
        }
        return articles;
    }

    public int articlesSize() {
        return getAll().size();
    }

    public List<Article> searchArticles(List<Article> sortedList, String search, String[] tags) {
        List<Article> searchArticles = new ArrayList<>();


        for (Article article : sortedList) {
            boolean shouldAdd = false;

            if (tags != null && tags.length > 0) {
                int i = 0;
                for (String tag : tags) {
                    for (String tagg : article.getCategory()) {

                        if (tag.equals(tagg)) {
                            shouldAdd = true;
                            break;

                        } else if (tag.equals(tags[tags.length - 1])) {
                            i++;
                        }
                    }
                }
                if (i == article.getCategory().length) {
                    continue;
                }
            }

            if (search != null) {
                if (article.getHeader().toLowerCase().trim().contains(search.toLowerCase().trim())) {
                    searchArticles.add(article);
                    continue;
                } else {
                    shouldAdd = false;
                }
            } else {
                shouldAdd = true;           // false when don't want search all articles without typing anything
            }

            if (shouldAdd) {
                searchArticles.add(article);
            }

        }
        return searchArticles;
    }


    public String tagsToRequest(String[] tags) {
        StringBuilder requestString = new StringBuilder();
        for (String s : tags) {
            requestString.append(s);
            if (!s.equals(tags[tags.length - 1]))
                requestString.append(",");
        }
        return requestString.toString();
    }

    public List<Article> articlesFromUserId(Long id) {
        return userServices.getById(id).getArticles();
    }

    public Comment getCommentById(Long id) {
        return commentsRepo.findOne(id);
    }

    public void addComment(Comment comment) {
        Article article = getArticleById(comment.getArticle().getId());
        article.setCommentsCounter(article.getVisitCounter() + 1);
        commentsRepo.save(comment);
    }

    public List<Article> getTop10SortedByVisits() {
        return articleRepo.findTop10ByOrderByVisitCounterDesc();
    }

    public List<Article> getAllSortedByVisits() {
        return articleRepo.findAllByOrderByVisitCounterDesc();
    }

    public List<Article> getArticlesWithoutWelcomePage() {
        List<Article> list = this.getAll();
        return list;
    }

    public BasicArticle getWelcomeArticle() {
        return basicArticleRepo.findOne(1L);
    }

    public int getNumberOfUserArticles(String username) {
        return userServices.getByName(username).getArticles().size();
    }

    public String getFirstBodyLetters(Long id) {
        return getArticleById(id).getBody().substring(0, 50);
    }

    public List<Article> getArticlesFromUser(String username) {
        return userServices.getByName(username).getArticles();
    }

    public List<Article> getAll() {
        return (List<Article>) articleRepo.findAll();
    }

    public Article getArticleById(Long id) {
        return articleRepo.findOne(id);
    }

    public void saveOrUpdate(Article article, String username) {
        if (article != null) {
            Users users = userServices.getByName(username);
            article.setUser(users);

            List<Article> articles = users.getArticles();
            articles.add(article);

            users.setArticles(articles);

            articleRepo.save(article);
        }
    }

    public Map<String, String> failures(ArticleDTO articleDTO) {
        Map<String, String> map = new HashMap<>();

        if (articleDTO.getTitle().length() < 6 && articleDTO.getTitle().length() > 0)
            map.put("TITLESHORT", "<p>Title was too short</p>");
        if (articleDTO.getTitle().length() > 40)
            map.put("TITLELONG", "<p>Title was too long</p>");
        else if (articleDTO.getTitle().isEmpty())
            map.put("TITLEEMPTY", "<p>Title can't be empty</p>");

        if (articleDTO.getBody().length() < 40)
            map.put("BODYSHORT", "<p>Try to say something more</p>");
        else if (articleDTO.getBody().isEmpty())
            map.put("BODYEMPTY", "<p>You have to write something</p>");

        /*if(articleDTO.getCategory().length == 0)
            map.put("CATEGOTY", "Pick atlest one category");*/

        if (map.size() > 0)
            return map;
        else
            return null;
    }

    public String[] getLabels() {
        return labels;
    }

    public String[] getSortOptions() {
        return sortOptions;
    }
}

