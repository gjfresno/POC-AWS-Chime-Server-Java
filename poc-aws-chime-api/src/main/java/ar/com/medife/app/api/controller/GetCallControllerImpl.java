package ar.com.medife.app.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.chimesdkmeetings.model.CreateAttendeeRequest;
import software.amazon.awssdk.services.chimesdkmeetings.ChimeSdkMeetingsClient;
import software.amazon.awssdk.services.chimesdkmeetings.model.CreateAttendeeResponse;
import software.amazon.awssdk.services.chimesdkmeetings.model.CreateMeetingRequest;
import software.amazon.awssdk.services.chimesdkmeetings.model.CreateMeetingResponse;
import software.amazon.awssdk.services.chimesdkmeetings.model.DeleteMeetingRequest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/call")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GetCallControllerImpl implements GetCallController {

    private final ChimeSdkMeetingsClient chimeClient;

    GetCallControllerImpl(){
        this.chimeClient = ChimeSdkMeetingsClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

    @GetMapping("/createVideoCall")
    public Map<String, Object> createVideoCall(@RequestParam String room, @RequestParam String user) {

        Map<String, Object> response = new HashMap<>();
        try {
            CreateMeetingRequest createMeetingRequest = CreateMeetingRequest.builder()
                    .clientRequestToken(room + "-" + System.currentTimeMillis()) // Token único
                    .externalMeetingId("room")
                    .mediaRegion("us-east-1")
                    .build();

            CreateMeetingResponse meetingResponse = chimeClient.createMeeting(createMeetingRequest);

            System.out.println("-------------------------------------------------------------");
            System.out.println(" MEETING CREATE ");
            System.out.println("-------------------------------------------------------------");
            System.out.println(meetingResponse.toString());
            System.out.println("-------------------------------------------------------------");

            CreateAttendeeRequest createAttendeeRequest = CreateAttendeeRequest.builder()
                    .meetingId(meetingResponse.meeting().meetingId())
                    .externalUserId(user)
                    .build();

            CreateAttendeeResponse createAttendeeResponse = chimeClient.createAttendee(createAttendeeRequest);

            System.out.println("-------------------------------------------------------------");
            System.out.println(" ATTENDEE CREATE ");
            System.out.println("-------------------------------------------------------------");
            System.out.println(createAttendeeResponse.toString());
            System.out.println("-------------------------------------------------------------");

            // Responder con información de la reunión
//            response.put("status", "success");
//            response.put("meetingId", meetingResponse.meeting().meetingId());
//            response.put("attendeeId", createAttendeeResponse.attendee().attendeeId());
//            response.put("joinToken", createAttendeeResponse.attendee().joinToken());

            Map<String, Object> mediaPlacement = new HashMap<>();
            mediaPlacement.put("AudioFallbackUrl", meetingResponse.meeting().mediaPlacement().audioFallbackUrl());
            mediaPlacement.put("AudioHostUrl", meetingResponse.meeting().mediaPlacement().audioHostUrl());
            mediaPlacement.put("TurnControlUrl", meetingResponse.meeting().mediaPlacement().turnControlUrl());
            mediaPlacement.put("SignalingUrl", meetingResponse.meeting().mediaPlacement().signalingUrl());
            mediaPlacement.put("EventIngestionUrl", meetingResponse.meeting().mediaPlacement().eventIngestionUrl());
            mediaPlacement.put("ScreenDataUrl", meetingResponse.meeting().mediaPlacement().screenDataUrl());
            mediaPlacement.put("ScreenSharingUrl", meetingResponse.meeting().mediaPlacement().screenSharingUrl());
            mediaPlacement.put("ScreenViewingUrl", meetingResponse.meeting().mediaPlacement().screenViewingUrl());

            Map<String, Object> meetingFeatures = new HashMap<>();
            Map<String, String> audioFeatures = new HashMap<>();
            audioFeatures.put("EchoReduction", "AVAILABLE");
            meetingFeatures.put("Audio", audioFeatures);

            Map<String, Object> meetingDetails = new HashMap<>();
            meetingDetails.put("ExternalMeetingId", room); // Nombre de la reunión
            meetingDetails.put("MediaPlacement", mediaPlacement);
            meetingDetails.put("MediaRegion", "us-east-1"); // Región del participante
            meetingDetails.put("MeetingArn", meetingResponse.meeting().meetingArn());
            meetingDetails.put("MeetingId", meetingResponse.meeting().meetingId());
            meetingDetails.put("MeetingFeatures", meetingFeatures);

            Map<String, Object> capabilities = new HashMap<>();
            capabilities.put("Audio", "SendReceive");
            capabilities.put("Video", "SendReceive");
            capabilities.put("Content", "SendReceive");

            Map<String, Object> attendeeDetails = new HashMap<>();
            attendeeDetails.put("AttendeeId", createAttendeeResponse.attendee().attendeeId());
            attendeeDetails.put("Capabilities", capabilities);
            attendeeDetails.put("ExternalUserId", user);
            attendeeDetails.put("JoinToken", createAttendeeResponse.attendee().joinToken());
            attendeeDetails.put("Name", user);

            Map<String, Object> joinInfo = new HashMap<>();
            joinInfo.put("meetingInfo", meetingDetails);
            joinInfo.put("attendeeInfo", attendeeDetails);

            response.put("joinInfo", joinInfo);

            System.out.println("-------------------------------------------------------------");
            System.out.println(" RESPONSE ");
            System.out.println("-------------------------------------------------------------");
            System.out.println(response.toString());
            System.out.println("-------------------------------------------------------------");

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    @GetMapping("/deleteVideoCall")
    public Map<String, String> deleteVideoCall(String room, String user) {
        Map<String, String> response = new HashMap<>();
        try {
            // Aquí asumimos que "room" es el ID de la reunión
            DeleteMeetingRequest deleteRequest = DeleteMeetingRequest.builder()
                    .meetingId(room) // ID de la reunión (debe ser almacenado en algún lugar al crearla)
                    .build();

            chimeClient.deleteMeeting(deleteRequest);

            // Responder con información de éxito
            response.put("status", "success");
            response.put("room", room);
            response.put("moderator", user);
            response.put("message", "Meeting successfully deleted.");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

}
