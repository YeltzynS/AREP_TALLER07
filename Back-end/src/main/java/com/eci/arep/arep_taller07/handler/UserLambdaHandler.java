package com.eci.arep.arep_taller07.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.eci.arep.arep_taller07.model.User;
import com.eci.arep.arep_taller07.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class UserLambdaHandler
  implements
    RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private final UserService userService = new UserService(); // Suponiendo que ya tienes un servicio que maneja la lógica de usuarios.
  private final ObjectMapper objectMapper = new ObjectMapper(); // Usamos ObjectMapper para convertir entre objetos y JSON.

  // Este es el método obligatorio para la implementación de RequestHandler
  @Override
  public APIGatewayProxyResponseEvent handleRequest(
    APIGatewayProxyRequestEvent input,
    Context context
  ) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

    try {
      String httpMethod = input.getHttpMethod(); // Obtenemos el método HTTP

      switch (httpMethod) {
        case "POST":
          return handleCreateUser(input); // Crear usuario
        case "GET":
          if (
            input.getPathParameters() != null &&
            input.getPathParameters().containsKey("id")
          ) {
            return handleGetUserById(input); // Obtener usuario por ID
          } else if (
            input.getPathParameters() != null &&
            input.getPathParameters().containsKey("email")
          ) {
            return handleGetUserByEmail(input); // Obtener usuario por email
          } else {
            return handleGetAllUsers(); // Obtener todos los usuarios
          }
        default:
          response.setStatusCode(405); // Método no permitido
          response.setBody("Method Not Allowed");
          break;
      }
    } catch (Exception e) {
      response.setStatusCode(500); // Error interno
      response.setBody("Internal Server Error: " + e.getMessage());
    }
    return response;
  }

  // Crear un nuevo usuario (POST)
  private APIGatewayProxyResponseEvent handleCreateUser(
    APIGatewayProxyRequestEvent input
  ) throws Exception {
    User user = objectMapper.readValue(input.getBody(), User.class); // Convertir el cuerpo de la solicitud JSON a un objeto User
    User createdUser = userService.createUser(user); // Llamar al servicio para crear el usuario
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    response.setStatusCode(201); // HTTP 201 - Creado
    response.setBody(objectMapper.writeValueAsString(createdUser)); // Convertir el objeto User a JSON y devolverlo
    return response;
  }

  // Obtener un usuario por ID (GET)
  private APIGatewayProxyResponseEvent handleGetUserById(
    APIGatewayProxyRequestEvent input
  ) throws Exception {
    Long userId = Long.parseLong(input.getPathParameters().get("id")); // Obtener el ID del parámetro de la ruta
    User user = userService.getUserById(userId); // Llamar al servicio para obtener el usuario
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    if (user != null) {
      response.setStatusCode(200); // HTTP 200 - OK
      response.setBody(objectMapper.writeValueAsString(user)); // Convertir el usuario a JSON
    } else {
      response.setStatusCode(404); // HTTP 404 - No encontrado
      response.setBody("User not found");
    }
    return response;
  }

  // Obtener un usuario por email (GET)
  private APIGatewayProxyResponseEvent handleGetUserByEmail(
    APIGatewayProxyRequestEvent input
  ) throws Exception {
    String email = input.getPathParameters().get("email"); // Obtener el email del parámetro de la ruta
    User user = userService.getUserByEmail(email); // Llamar al servicio para obtener el usuario por email
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    if (user != null) {
      response.setStatusCode(200); // HTTP 200 - OK
      response.setBody(objectMapper.writeValueAsString(user)); // Convertir el usuario a JSON
    } else {
      response.setStatusCode(404); // HTTP 404 - No encontrado
      response.setBody("User not found");
    }
    return response;
  }

  // Obtener todos los usuarios (GET)
  private APIGatewayProxyResponseEvent handleGetAllUsers() throws Exception {
    List<User> users = userService.getAllUsers(); // Llamar al servicio para obtener todos los usuarios
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    response.setStatusCode(200); // HTTP 200 - OK
    response.setBody(objectMapper.writeValueAsString(users)); // Convertir la lista de usuarios a JSON
    return response;
  }
}
