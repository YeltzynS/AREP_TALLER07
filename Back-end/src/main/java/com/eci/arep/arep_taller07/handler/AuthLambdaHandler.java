package com.eci.arep.arep_taller07.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.eci.arep.arep_taller07.model.Post;
import com.eci.arep.arep_taller07.model.PostStream;
import com.eci.arep.arep_taller07.model.User;
import com.eci.arep.arep_taller07.service.PostService;
import com.eci.arep.arep_taller07.service.PostStreamService;
import com.eci.arep.arep_taller07.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class AuthLambdaHandler
  implements
    RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private final UserService userService = new UserService(); // Suponiendo que el servicio UserService está implementado
  private final PostService postService = new PostService(); // Suponiendo que el servicio PostService está implementado
  private final PostStreamService postStreamService = new PostStreamService(); // Suponiendo que el servicio PostStreamService está implementado
  private final ObjectMapper objectMapper = new ObjectMapper(); // Para convertir objetos a JSON

  private static final Long SHARED_POST_STREAM_ID = 1L; // ID del PostStream compartido

  @Override
  public APIGatewayProxyResponseEvent handleRequest(
    APIGatewayProxyRequestEvent input,
    Context context
  ) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

    try {
      // Extraer datos del cuerpo de la solicitud
      Map<String, String> request = objectMapper.readValue(
        input.getBody(),
        Map.class
      );
      String name = request.get("name");
      String email = request.get("email");
      String password = request.get("password");
      String postContent = request.get("postContent");

      // Validar la longitud del post
      if (postContent.length() > 140) {
        throw new RuntimeException(
          "El post no puede tener más de 140 caracteres."
        );
      }

      // Crear el usuario
      User user = new User();
      user.setName(name);
      user.setEmail(email);
      user.setPassword(password);
      User savedUser = userService.createUser(user);

      // Obtener el PostStream compartido o crearlo si no existe
      PostStream sharedPostStream = postStreamService.getStream(
        SHARED_POST_STREAM_ID
      );
      if (sharedPostStream == null) {
        // Crear un nuevo PostStream si no existe
        sharedPostStream = new PostStream();
        sharedPostStream.setId(SHARED_POST_STREAM_ID);
        sharedPostStream.setName("Stream Principal");
        sharedPostStream = postStreamService.createStream(sharedPostStream);
      }

      // Crear el post asociado al usuario y al PostStream compartido
      Post post = new Post();
      post.setContent(postContent);
      post.setUser(savedUser);
      post.setPostStream(sharedPostStream);
      Post savedPost = postService.createPost(post);

      // Preparar la respuesta con el usuario y el post creados
      Map<String, Object> responseBody = new HashMap<>();
      responseBody.put("user", savedUser);
      responseBody.put("post", savedPost);

      // Configurar la respuesta
      response.setStatusCode(200); // HTTP 200 OK
      response.setBody(objectMapper.writeValueAsString(responseBody));
    } catch (Exception e) {
      response.setStatusCode(500); // HTTP 500 - Error interno
      response.setBody("Error: " + e.getMessage());
    }
    return response;
  }
}
