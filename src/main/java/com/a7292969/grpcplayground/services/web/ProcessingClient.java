package com.a7292969.grpcplayground.services.web;

import com.a7292969.grpcplayground.MyServiceGrpc;
import com.a7292969.grpcplayground.Processing;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ProcessingClient {
    @GrpcClient("procClient")
    private MyServiceGrpc.MyServiceBlockingStub blockingStub;

    public byte[] drawText(String text) {
        Processing.DrawReply res = blockingStub.drawText(Processing.DrawRequest.newBuilder().setText(text).build());
        return res.getPngImage().toByteArray();
    }
}
