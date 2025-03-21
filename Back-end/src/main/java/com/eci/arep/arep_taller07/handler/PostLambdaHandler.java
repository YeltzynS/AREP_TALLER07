package com.eci.arep.arep_taller07.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.eci.arep.arep_taller07.model.Post;
import com.eci.arep.arep_taller07.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class PostLambdaHandler
  implements
    RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private final PostService postService = new PostService(); // Suponiendo que ya tienes el servicio Post
  private final ObjectMapper objectMapper = new ObjectMapper(); // Para convertir objetos a JSON

  @Override
  public APIGatewayProxyResponseEvent handleRequest(
    APIGatewayProxyRequestEvent input,
    Context context
  ) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

    try {
      String httpMethod = input.getHttpMethod(); // Obtener el método HTTP (GET, POST)
      switch (httpMethod) {
        case "POST":
          return handleCreatePost(input); // Crear un nuevo Post
        case "GET":
          return handleGetPostsByStream(input); // Obtener los posts por Stream
        default:
          response.setStatusCode(405); // Método no permitido
          response.setBody("Method Not Allowed");
          break;
      }
    } catch (Exception e) {
      response.setStatusCode(500); // Error del servidor
      response.setBody("Internal Server Error: " + e.getMessage());
    }
    return response;
  }

  // Crear un nuevo Post
  private APIGatewayProxyResponseEvent handleCreatePost(
    APIGatewayProxyRequestEvent input
  ) throws Exception {
    Post post = objectMapper.readValue(input.getBody(), Post.class); // Convertir el cuerpo de la solicitud a un objeto Post
    if (post.getContent().length() > 140) {
      throw new RuntimeException(
        "El post no puede tener más de 140 caracteres."
      );
    }
    Post createdPost = postService.createPost(post); // Llamar al servicio para crear el Post
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    response.setStatusCode(201); // HTTP 201 - Creado
    response.setBody(objectMapper.writeValueAsString(createdPost)); // Convertir el objeto Post a JSON
    return response;
  }

  // Obtener los posts por Stream
  private APIGatewayProxyResponseEvent handleGetPostsByStream(
    APIGatewayProxyRequestEvent input
  ) throws Exception {
    Long postStreamId = Long.parseLong(
      input.getPathParameters().get("postStreamId")
    ); // Obtener el ID del parámetro de la ruta
    List<Post> posts = postService.getPostsByStream(postStreamId); // Llamar al servicio para obtener los posts por Stream
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    if (!posts.isEmpty()) {
      response.setStatusCode(200); // HTTP 200 - OK
      response.setBody(objectMapper.writeValueAsString(posts)); // Convertir la lista de posts a JSON
    } else {
      response.setStatusCode(404); // HTTP 404 - No encontrado
      response.setBody("No posts found for this Stream");
    }
    return response;
  }
}
