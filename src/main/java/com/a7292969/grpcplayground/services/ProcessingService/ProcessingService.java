package com.a7292969.grpcplayground.services.ProcessingService;

import com.a7292969.grpcplayground.MyServiceGrpc;
import com.a7292969.grpcplayground.Processing;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@GrpcService
public class ProcessingService extends MyServiceGrpc.MyServiceImplBase {
    @Override
    public void drawText(Processing.DrawRequest request, StreamObserver<Processing.DrawReply> responseObserver) {
        var img = new BufferedImage(1024, 256, BufferedImage.TYPE_3BYTE_BGR);

        var g = img.createGraphics();
        g.setFont(g.getFont().deriveFont(150.0f));
        g.drawString(request.getText(), 50.0f, 170.0f);
        g.dispose();

        var baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(img, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        var reply = Processing.DrawReply.newBuilder()
                .setPngImage(ByteString.copyFrom(baos.toByteArray())).build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
