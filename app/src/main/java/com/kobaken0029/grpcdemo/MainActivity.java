package com.kobaken0029.grpcdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kobaken0029.grpcdemo.proto.CreateRequest;
import com.kobaken0029.grpcdemo.proto.DeleteRequest;
import com.kobaken0029.grpcdemo.proto.GetAllRequest;
import com.kobaken0029.grpcdemo.proto.GetRequest;
import com.kobaken0029.grpcdemo.proto.Sex;
import com.kobaken0029.grpcdemo.proto.UpdateRequest;
import com.kobaken0029.grpcdemo.proto.User;
import com.kobaken0029.grpcdemo.proto.UserServiceGrpc;

import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String userName = "kobaken";

        // Create channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("10.0.2.2", 50051)
                .usePlaintext(true)
                .build();

        // Create stub for UserService
        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

        // To Create
        CreateRequest createRequest = CreateRequest.newBuilder()
                .setAge(23)
                .setName(userName)
                .setSex(Sex.MALE)
                .build();
        String createResultMessage = stub.create(createRequest).getMessage();
        Log.d(TAG, "[Create] " + createResultMessage);

        // To Get
        GetRequest getRequest = GetRequest.newBuilder()
                .setName(userName)
                .build();
        User user = stub.get(getRequest).getUser();
        Log.d(TAG, "[Get] " + user.getName() + ":" + user.getAge() + ":" + user.getSex().name());

        // To Update
        UpdateRequest updateRequest = UpdateRequest.newBuilder()
                .setName(userName)
                .setAge(30)
                .setSex(Sex.FEMALE)
                .build();
        String updateResultMessage = stub.update(updateRequest).getMessage();
        Log.d(TAG, "[Update] " + updateResultMessage);

        // Check user has been changed
        User updatedUser = stub.get(getRequest).getUser();
        Log.d(TAG, "[Get] " + updatedUser.getName() + ":" + updatedUser.getAge()
                + ":" + updatedUser.getSex().name());

        // To Delete
        DeleteRequest deleteRequest = DeleteRequest.newBuilder()
                .setName(userName)
                .build();
        String deleteResultMessage = stub.delete(deleteRequest).getMessage();
        Log.d(TAG, "[Delete] " + deleteResultMessage);

        // To GetAll
        // Create two user
        stub.create(CreateRequest.newBuilder()
                .setAge(22)
                .setName("Mizuki Sonoko")
                .setSex(Sex.MALE)
                .build());
        stub.create(CreateRequest.newBuilder()
                .setAge(21)
                .setName("upamune")
                .setSex(Sex.MALE)
                .build());
        GetAllRequest getAllRequest = GetAllRequest.newBuilder().build();
        List<User> users = stub.getAll(getAllRequest).getUsersList();
        for (User u: users) {
            Log.d(TAG, "[GetAll] " + u.getName() + ":" + u.getAge() + ":" + u.getSex().name());
        }
    }
}
