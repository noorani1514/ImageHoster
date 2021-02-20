package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.Tag;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageController imageController;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String addComment(@PathVariable("imageId") Integer imageId, @PathVariable("imageTitle") String imageTitle,
                             @RequestParam(name = "comment") String comment, Comment newComment, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("loggeduser");
        newComment.setUser(user);
        newComment.setImage(imageService.getImageById(imageId));
        newComment.setText(comment);
        newComment.setCreatedDate(new Date());
        commentService.addComment(newComment);

        //return "redirect:/images/{imageId}/{imageTitle}";

        return "redirect:/images";
    }

    @RequestMapping("comments/{imageId}")
    public String getUserComments(@PathVariable("imageId") Integer imageId, Model model) {
        List<Comment> comment = commentService.getComment(imageId);
        model.addAttribute("comments", comment);
        return "images/image";
    }

}
