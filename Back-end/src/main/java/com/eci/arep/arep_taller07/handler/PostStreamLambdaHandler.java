package com.eci.arep.arep_taller07.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.eci.arep.arep_taller07.model.PostStream;
import com.eci.arep.arep_taller07.service.PostStreamService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostStreamLambdaHandler
  implements
    RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private final PostStreamService postStreamService = new PostStreamService(); // Suponiendo que ya tienes el servicio PostStream
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
          return handleCreateStream(input); // Crear un nuevo PostStream
        case "GET":
          return handleGetStreamById(input); // Obtener un PostStream por ID
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

  // Crear un nuevo PostStream
  private APIGatewayProxyResponseEvent handleCreateStream(
    APIGatewayProxyRequestEvent input
  ) throws Exception {
    PostStream postStream = objectMapper.readValue(
      input.getBody(),
      PostStream.class
    ); // Convertir el cuerpo a un objeto PostStream
    PostStream createdStream = postStreamService.createStream(postStream); // Llamar al servicio para crear el PostStream
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    response.setStatusCode(201); // HTTP 201 - Creado
    response.setBody(objectMapper.writeValueAsString(createdStream)); // Convertir el objeto PostStream a JSON
    return response;
  }

  // Obtener un PostStream por ID
  private APIGatewayProxyResponseEvent handleGetStreamById(
    APIGatewayProxyRequestEvent input
  ) throws Exception {
    Long postStreamId = Long.parseLong(input.getPathParameters().get("id")); // Obtener el ID del parámetro de la ruta
    PostStream postStream = postStreamService.getStream(postStreamId); // Llamar al servicio para obtener el PostStream
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    if (postStream != null) {
      response.setStatusCode(200); // HTTP 200 - OK
      response.setBody(objectMapper.writeValueAsString(postStream)); // Convertir el objeto PostStream a JSON
    } else {
      response.setStatusCode(404); // HTTP 404 - No encontrado
      response.setBody("PostStream not found");
    }
    return response;
  }
}
