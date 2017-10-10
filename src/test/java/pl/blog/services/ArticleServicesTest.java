package pl.blog.services;

import org.junit.Assert;
import org.junit.Test;
import pl.blog.dto.ArticleDTO;

import java.util.Map;

/**
 * Created by Mike on 27.07.2017.
 */
public class ArticleServicesTest {
    @Test
    public void faliures() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        ArticleServices services = new ArticleServices();

        articleDTO.setTitle("LOL");
        articleDTO.setBody("Było co szłość programistycznego śwro");

        Map<String, String> map = services.failures(articleDTO);
        Assert.assertNotNull("MAP ARTICLE NULL", map);
        Assert.assertFalse("MAP ARTICLE EMPTY", map.isEmpty());
        Assert.assertTrue(map.size() == 2);
    }

}