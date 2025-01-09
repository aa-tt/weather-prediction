//package life.outorin.myday.repository;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.DocumentReference;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.FirestoreOptions;
//import com.google.cloud.firestore.WriteResult;
//import life.outorin.myday.dto.WeatherReport;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Repository;
//
//import java.io.IOException;
//import java.time.ZoneOffset;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//
//@Repository
//public class FirestoreRepository implements WeatherDataRepository {
//
//    private final Firestore db;
//
//    public FirestoreRepository(@Value("${spring.cloud.gcp.firestore.credentials.location}") Resource credentialsPath) throws IOException {
//        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
//                .setCredentials(GoogleCredentials.fromStream(credentialsPath.getInputStream()))
//                .build();
//        this.db = firestoreOptions.getService();
//    }
//
//    @Override
//    public void saveWeatherReport(String city, WeatherReport report) throws ExecutionException, InterruptedException {
//        Map<String, Object> data = new HashMap<>();
//        data.put("city", city);
//        data.put("timestamp", report.dt().toEpochSecond(ZoneOffset.UTC));
//        data.put("alerts", report.alerts());
//        data.put("mains", report.mains());
//        data.put("temp_min", report.temp_min());
//        data.put("temp_max", report.temp_max());
//
//        DocumentReference docRef = db.collection("WeatherData").document();
//        WriteResult result = docRef.set(data).get();
//        System.out.println("Update time : " + result.getUpdateTime());
//    }
//}