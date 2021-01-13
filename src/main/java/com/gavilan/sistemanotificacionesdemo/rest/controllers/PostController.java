package com.gavilan.sistemanotificacionesdemo.rest.controllers;

import com.gavilan.sistemanotificacionesdemo.model.entities.Post;
import com.gavilan.sistemanotificacionesdemo.model.exceptions.NotisException;
import com.gavilan.sistemanotificacionesdemo.model.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<?> nuevoPost(@RequestBody Post post) {
        Map<String, Object> response = new HashMap<>();
        Post postCreado;

        try {
            postCreado = this.postService.crearPost(post);
        } catch (NotisException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("post", postCreado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> listarPosts() {
        Map<String, Object> response = new HashMap<>();
        List<Post> posts;

        try {
            posts = this.postService.listarPosts();
        } catch (NotisException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("posts", posts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/posts/by-username/{username}")
    public ResponseEntity<?> obtenerPostsDeUsuario(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        List<Post> postsByUser;

        try {
            postsByUser = this.postService.obtenerPostPorUsuario(username);
        } catch (NotisException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("posts", postsByUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/posts/{idPost}")
    public ResponseEntity<?> obtenerPost(@PathVariable Long idPost) {
        Map<String, Object> response = new HashMap<>();
        Post post;

        try {
            post = this.postService.obtenerPost(idPost);
        } catch (NotisException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("post", post);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
