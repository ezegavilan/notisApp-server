package com.gavilan.sistemanotificacionesdemo.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "post_custom_sequence",
            strategy = "com.gavilan.sistemanotificacionesdemo.model.entities.idgenerator.PostIdGenerator")
    @GeneratedValue(generator = "post_custom_sequence")
    @Column(name = "post_id")
    private String postId;
    private String nombrePost;
    private String descripcion;
    private Date creadoEn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;
}
