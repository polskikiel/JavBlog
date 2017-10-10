package pl.blog.services.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.blog.domain.Article;
import pl.blog.domain.Comment;
import pl.blog.domain.Users;
import pl.blog.dto.ArticleDTO;
import pl.blog.repos.ArticleRepo;
import pl.blog.services.ArticleServices;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 10.08.2017.
 */
@Component
public class ArticlesCreator {
    ArticleRepo articleRepo;
    ArticleServices articleServices;
    Users adminUser;
    Article customArticle;

    @Autowired
    public ArticlesCreator(ArticleRepo articleRepo, ArticleServices articleServices, Users adminUser, Article customArticle) {
        this.articleRepo = articleRepo;
        this.articleServices = articleServices;
        this.adminUser = adminUser;
        this.customArticle = customArticle;
    }

    @PostConstruct
    public void makeArticle() {
        articleRepo.save(customArticle);

        customArticle.setHeader("Spring Security custom user");
        customArticle.setBody("How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?" +
                "How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?How to create user with custom fields in database in Spring security?");
        customArticle.setImgUrl("https://www.techdotmatrix.com/wp-content/uploads/2016/10/Programming-languages.jpg");
        customArticle.setCategory(new String[]{"Spring", "Security", "Boot", "Data", "Java"});

        Long id = 2L;
        customArticle.setId(id++);
        articleRepo.save(customArticle);

        Comment comment = new Comment("TEST COMMENT", adminUser, customArticle);
        articleServices.addComment(comment);

        customArticle.setHeader("How to create services?");
        customArticle.setBody("Services are just a logic in your app, but how to make good usage of that?" +
                "Services should allow to save, edit database and they have to be annotated with @Service annotations.");
        customArticle.setImgUrl("http://theprogrammings.com/wp-content/uploads/2016/06/learn-to-code-what-is-programming.jpg");
        customArticle.setCategory(new String[]{"Spring", "Mvc", "Data", "Java"});

        customArticle.setId(id++);
        articleRepo.save(customArticle);


        customArticle.setBody("In dictum felis erat, at commodo sem tempor in. Sed aliquet, nibh vel ultrices laoreet, ligula nibh hendrerit erat, eu posuere dui augue eget lectus. Maecenas sodales, sem ut laoreet sagittis, sem diam auctor erat, at bibendum lacus augue sit amet risus. Duis eget tempor ante. Donec porta ligula magna, sit amet laoreet odio congue ac. Pellentesque vitae mattis purus. Duis mauris sapien, luctus sit amet aliquet sed, pretium quis orci." +
                "Nam posuere justo quis eros venenatis, eget pretium felis molestie. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras tristique semper interdum. Etiam ullamcorper imperdiet libero. Nunc euismod nunc faucibus risus auctor tempus. Fusce eleifend in dui eu rutrum. In at ornare dolor, vel sodales ipsum. Duis vulputate congue nisl sit amet lacinia. Vivamus pharetra fermentum justo ac pulvinar. Maecenas maximus pellentesque urna in condimentum." +
                "In hac habitasse platea dictumst. Cras in mi a purus congue faucibus. In volutpat mauris metus, id hendrerit felis molestie vitae. Cras vitae justo suscipit, aliquam nulla vitae, viverra ipsum. Nullam at fermentum libero, ut congue augue. Aliquam non nisl tortor. Proin sodales, augue sit amet suscipit auctor, tortor ipsum vulputate massa, quis rhoncus odio tellus vel leo. Mauris quis sapien bibendum, pretium nisl et, gravida neque. Suspendisse ut tincidunt arcu. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus." +
                "Nam luctus orci turpis, a dictum libero blandit a. Proin vestibulum dolor sit amet libero malesuada, interdum fringilla eros blandit. Vivamus risus elit, rutrum nec ullamcorper nec, dapibus vel turpis. Nullam blandit, leo quis tincidunt sodales, velit magna efficitur dui, non placerat nisl nisi non purus. Morbi feugiat dolor consequat nisi aliquam porttitor. Vivamus interdum mollis augue consequat lobortis. Proin dignissim accumsan interdum. Quisque mollis et urna sed rutrum. Quisque non justo viverra, consectetur sapien suscipit, luctus sapien. Vivamus enim nibh, convallis non imperdiet ut, gravida non nunc. Cras lectus lorem, interdum ut ornare id, facilisis in sem. Nulla rhoncus nisl eu quam consequat, ac fermentum risus dignissim. Maecenas sapien ante, blandit a suscipit at, fringilla eu leo. Ut fermentum purus urna, a rhoncus ex maximus non. Donec maximus eleifend aliquam." +
                "Praesent elementum justo non massa sodales, eget mattis dui cursus. Proin elementum erat elit, nec aliquet orci consequat non. Morbi lorem risus, cursus at tristique ut, dignissim id velit. Ut quis turpis eu mi gravida pulvinar. Aenean a lobortis dui. Duis volutpat at purus ut auctor. Integer mattis rutrum massa quis viverra. Fusce tincidunt, ante non interdum sollicitudin, nunc augue mattis neque, ac pharetra ante lectus nec lacus. Praesent finibus nisi nec condimentum elementum. Quisque felis metus, feugiat in risus nec, elementum aliquam turpis. Maecenas pellentesque aliquet turpis, ac malesuada neque gravida eget. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas erat sem, posuere eu dignissim nec, vulputate scelerisque urna. Donec tempus lectus turpis, id elementum mi scelerisque et. Vivamus posuere, ante in sollicitudin suscipit, massa nisi ornare odio, quis aliquet est libero vitae augue. Donec elementum metus eros, molestie efficitur quam varius a." +
                "Morbi pulvinar nulla sed tincidunt ullamcorper. Vivamus porttitor quam sit amet turpis semper, ac elementum nulla finibus. Quisque libero nunc, facilisis eget neque sit amet, tincidunt pulvinar ex. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean pharetra euismod purus, quis pulvinar tellus euismod ac. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Proin a arcu a ex feugiat tempus." +
                "Quisque turpis odio, malesuada at quam a, malesuada egestas nulla. Proin scelerisque ut dolor eget elementum. Aenean odio ex, aliquam sit amet est mollis, maximus commodo eros. Etiam ut tellus nec est viverra rutrum sit amet in lacus. Etiam blandit leo sed arcu volutpat, at pharetra ante maximus. Donec ac quam scelerisque, varius libero in, pulvinar nisi. Vestibulum eget ipsum rutrum, fringilla eros vitae, finibus felis. Suspendisse ac mauris quis sapien condimentum consectetur." +
                "Vivamus placerat mauris id enim tempus, vel egestas lorem finibus. Praesent lacinia vehicula metus eget fermentum. Sed ut sagittis lorem. Nullam id mi eros. Nullam neque nibh, elementum eu mauris nec, consectetur elementum tortor. Duis sed malesuada nisi. Aenean eu sollicitudin odio. Etiam tellus nibh, placerat sit amet convallis id, mattis vitae sapien. Curabitur ut nisl non ipsum volutpat dictum. Sed sed magna id nisl auctor ultrices. Donec pretium ornare libero eget tempus. Morbi consequat ex a mauris luctus scelerisque. Donec quis blandit nisl. Sed a odio sagittis diam rutrum laoreet. Donec malesuada, libero ut ultricies gravida, est lectus volutpat libero, id efficitur urna dui sed lorem. Donec viverra consequat gravida." +
                "Pellentesque ullamcorper, enim vitae lobortis tempus, ante tellus convallis nibh, id molestie neque mauris vitae diam. Phasellus velit nulla, viverra sed felis et, faucibus tempor augue. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vestibulum nulla eget porttitor dignissim. Curabitur eget congue diam. Mauris consectetur tristique tellus, id interdum erat. Curabitur volutpat maximus quam eget sollicitudin. Nullam pellentesque sapien non ante condimentum, sed vehicula sem vulputate. Morbi in tortor viverra, cursus ante eget, blandit justo. Ut iaculis blandit nunc, at auctor nibh porttitor ut. Sed egestas, libero nec condimentum faucibus, felis tellus venenatis augue, vel finibus nisl tellus vel arcu. Donec non convallis magna, nec dapibus tellus." +
                "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Maecenas dignissim, metus et varius varius, nunc elit consectetur libero, vitae maximus quam lacus nec neque. Etiam tempus, augue sit amet aliquam scelerisque, sem est egestas magna, ac ultricies lacus ex vitae eros. Aenean tincidunt tortor ac lorem tincidunt rutrum. Etiam sed risus sollicitudin, tristique neque non, malesuada enim. Praesent arcu felis, mattis in tincidunt vitae, viverra ac nunc. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi ut condimentum dolor. Cras volutpat massa eget nulla interdum auctor id eget ligula. Aliquam eget mollis tellus, vel vestibulum nunc. Nulla purus diam, malesuada vel magna ac, iaculis convallis lectus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aliquam efficitur turpis erat, eget volutpat metus mollis a. Morbi id quam urna. Donec vitae dignissim ipsum." +
                "Proin at velit eget risus accumsan tincidunt. Praesent suscipit turpis nisl. Ut eu enim efficitur, ullamcorper ligula sed, pellentesque quam. Nunc neque turpis, dignissim vitae pharetra in, ornare sed quam. Nulla tincidunt mauris posuere, mattis arcu et, tempus lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Fusce sit amet commodo odio. Fusce eget massa sit amet est bibendum auctor. Etiam sagittis tempus sapien, ac iaculis ipsum laoreet id. Cras efficitur nec eros vel pretium. Donec bibendum lorem in velit faucibus, eget rhoncus ipsum sollicitudin. Ut non tincidunt dui. Vestibulum ligula sapien, lobortis ut nunc ac, accumsan luctus turpis. Curabitur in fermentum nisl, in consequat erat." +
                "Donec fermentum eros semper elit feugiat, vel auctor sapien elementum. In ac maximus mauris. Cras tortor urna, ultrices eget tellus in, varius posuere metus. In a scelerisque urna. Integer eget elit rutrum, dapibus nulla sit amet, porttitor mi. Duis aliquam magna eu rhoncus rutrum. Pellentesque non tortor et elit laoreet molestie. Donec ultrices viverra fermentum. Phasellus non ligula eros. Vivamus condimentum interdum ante vitae porta. Sed maximus ex in efficitur feugiat. Morbi nec nibh lacus. Ut auctor mauris id feugiat volutpat. Curabitur quis neque vulputate, dapibus lorem at, ultrices dolor. Suspendisse potenti. Quisque id velit sit amet sem gravida auctor ut sit amet sem." +
                "Etiam in nibh et arcu suscipit vulputate. Maecenas tincidunt dapibus ex, vel rhoncus libero suscipit a. Mauris vestibulum neque vitae mi mattis, vel aliquam est bibendum. Aenean ut fermentum enim. Nulla facilisi. Nam porta lectus eu sem dignissim, a feugiat ante finibus. Morbi id nunc elit. Sed consectetur augue eu venenatis ullamcorper. Donec viverra elementum purus id rutrum. Mauris id erat quis nisl auctor venenatis. Vivamus eget eleifend tellus, a aliquam sapien. Sed imperdiet ac ligula eu consequat." +
                "Aenean imperdiet eros nec erat lacinia, quis porta nisi condimentum. Maecenas id vehicula diam. Donec eget diam eu tellus elementum interdum. Curabitur facilisis rhoncus molestie. Pellentesque ultricies commodo ligula, ac laoreet justo posuere et. Aliquam eros dui, mattis eu lobortis quis, commodo convallis leo. In hac habitasse platea dictumst." +
                "Sed non risus sed justo sodales suscipit. Mauris arcu massa, varius ut ligula id, mattis scelerisque eros. Sed fermentum elit tincidunt porta malesuada. Suspendisse condimentum justo id ex eleifend, sed posuere dui maximus. Maecenas tempor porttitor magna id pretium. Curabitur auctor semper magna, at tincidunt mi sollicitudin eu. Integer risus elit, pretium sit amet egestas id, bibendum eu diam.");
        customArticle.setImgUrl("http://theprogrammings.com/wp-content/uploads/2016/06/learn-to-code-what-is-programming.jpg");
        customArticle.setCategory(new String[]{"Java", "Other"});
        for(; id < 20; id++) {
            customArticle.setHeader("Ipsum Lorem " + id.toString());
            customArticle.setId(id);

            articleRepo.save(customArticle);
        }

    }
}
