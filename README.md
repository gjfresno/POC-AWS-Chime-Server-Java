
# POC AWS Chime SDK para Java

Este repositorio demuestra una Prueba de Concepto (POC) utilizando el AWS Chime SDK para Java. Se basa en el cliente SDK de AWS:

[ChimeSdkMeetingsClient](https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/chimesdkmeetings/ChimeSdkMeetingsClient.html#getMeeting\(software.amazon.awssdk.services.chimesdkmeetings.model.GetMeetingRequest\))

## Descripción General

El código proporciona dos endpoints para gestionar videollamadas:

### Endpoints

1. **Crear Videollamada**

   ```bash
   curl --location 'http://localhost:8085/api/v1/call/createVideoCall?room=nombreSala&user=nombreCoordinador'
   ```

    - **Parámetros de consulta:**
        - `room`: El nombre de la sala de reunión a crear.
        - `user`: El nombre del coordinador que crea la sala.

    - **Respuesta:**

      ```json
      {
        "joinInfo": {
          "meetingInfo": {
            "MeetingFeatures": {
              "Audio": {
                "EchoReduction": "AVAILABLE"
              }
            },
            "MeetingId": "1b66e8c7-16a0-436c-8c11-dd1fbfe42713",
            "MeetingArn": "arn:aws:chime:us-east-1:587334433628:meeting/1b66e8c7-16a0-436c-8c11-dd1fbfe42713",
            "MediaRegion": "us-east-1",
            "MediaPlacement": {
              "EventIngestionUrl": "https://data.svc.ue1.ingest.chime.aws/v1/client-events",
              "ScreenViewingUrl": "wss://bitpw.m2.ue1.app.chime.aws:443/ws/connect?passcode=null&viewer_uuid=null&X-BitHub-Call-Id=1b66e8c7-16a0-436c-8c11-dd1fbfe42713",
              "AudioFallbackUrl": "wss://wss.k.m2.ue1.app.chime.aws:443/calls/1b66e8c7-16a0-436c-8c11-dd1fbfe42713",
              "TurnControlUrl": "https://2713.cell.us-east-1.meetings.chime.aws/v2/turn_sessions",
              "ScreenDataUrl": "wss://bitpw.m2.ue1.app.chime.aws:443/v2/screen/1b66e8c7-16a0-436c-8c11-dd1fbfe42713",
              "ScreenSharingUrl": "wss://bitpw.m2.ue1.app.chime.aws:443/v2/screen/1b66e8c7-16a0-436c-8c11-dd1fbfe42713",
              "AudioHostUrl": "3d44abaec55e4a79068b9bd485978279.k.m2.ue1.app.chime.aws:3478",
              "SignalingUrl": "wss://signal.m2.ue1.app.chime.aws/control/1b66e8c7-16a0-436c-8c11-dd1fbfe42713"
            },
            "ExternalMeetingId": "nombreSala"
          },
          "attendeeInfo": {
            "Capabilities": {
              "Video": "SendReceive",
              "Content": "SendReceive",
              "Audio": "SendReceive"
            },
            "ExternalUserId": "carlitos",
            "AttendeeId": "dd636aef-cfa9-5401-f5ac-b4457bcfca60",
            "JoinToken": "ZGQ2MzZhZWYtY2ZhOS01NDAxLWY1YWMtYjQ0NTdiY2ZjYTYwOmRmMzFhMWI2LTI5ODctNDczMi1hYzRmLTQ4ZjMxMjYxMzExNw",
            "Name": "carlitos"
          }
        }
      }
      ```

2. **Eliminar Videollamada**

   ```bash
   curl --location 'http://localhost:8085/api/v1/call/deleteVideoCall?room=meetid'
   ```

    - **Parámetros de consulta:**
        - `room`: El ID único de la reunión a eliminar.

## Cómo Ejecutar el Proyecto

Sigue estos pasos para compilar y ejecutar la aplicación localmente o utilizando Docker.

### Prerrequisitos

- **Java Development Kit (JDK):** Asegúrate de tener JDK 11 o superior instalado.
- **Maven:** Necesario para compilar el proyecto.
- **Docker:** Asegúrate de tener Docker instalado y en ejecución.

### Pasos

1. **Compilar el proyecto**

   ```bash
   mvn clean package
   ```

2. **Construir la imagen de Docker**

   ```bash
   docker build . -t poc_chime_java
   ```

3. **Ejecutar el contenedor de Docker**

   ```bash
   docker run -p 8085:8085 -t poc_chime_java
   ```

Una vez que el contenedor esté en ejecución, los endpoints estarán disponibles en `http://localhost:8085`.

## Notas

- Asegúrate de que tus credenciales y configuración de AWS estén correctamente configuradas para que el SDK funcione.
- Si encuentras problemas con los puertos o las variables de entorno, ajusta la configuración del Docker según sea necesario.

---

