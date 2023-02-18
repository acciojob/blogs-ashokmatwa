package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository1;

    @Autowired
    UserRepository userRepository1;

    public Blog createAndReturnBlog(Integer userId, String title, String content) throws Exception{
        //create a blog at the current time
        if(!userRepository1.findById(userId).isPresent())
            throw new Exception();

        Blog blog = new Blog();

        //updating the blog details
        blog.setTitle(title);
        blog.setContent(content);

        //Updating the userInformation and changing its blogs
        User user = userRepository1.findById(userId).get();
        List<Blog> blogList = user.getBlogList();
        blogList.add(blog);
        user.setBlogList(blogList);

        blog.setUser(user);//foreign key

        userRepository1.save(user);//cascade effect

        return blog;
    }

    public void deleteBlog(int blogId) throws Exception{
        //delete blog and corresponding images

        if(!blogRepository1.findById(blogId).isPresent())
            throw new Exception();

        Blog blog = blogRepository1.findById(blogId).get();

        //delete from user also ??
        User user = blog.getUser();
        List<Blog> blogList = user.getBlogList();
        blogList.remove(blog);
        user.setBlogList(blogList);

        userRepository1.save(user);

        //for images ?? --> cascade ??
        blogRepository1.deleteById(blogId); // corresponding images automatically delete --> cascade
    }
}
