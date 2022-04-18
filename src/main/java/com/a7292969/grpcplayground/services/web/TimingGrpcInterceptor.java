package com.a7292969.grpcplayground.services.web;

import io.grpc.*;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.core.annotation.Order;

@Order(2)
@GrpcGlobalClientInterceptor
public class TimingGrpcInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel channel) {

        return new ForwardingClientCall.SimpleForwardingClientCall<>(channel.newCall(method, callOptions)) {
            long startT;

            @Override
            public void sendMessage(ReqT message) {
                startT = System.currentTimeMillis();
                super.sendMessage(message);
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<>(responseListener) {
                    @Override
                    public void onClose(Status status, Metadata trailers) {
                        var endT = System.currentTimeMillis();
                        System.out.println(method.getBareMethodName() + " call time: " + (endT - startT) + " ms");
                        super.onClose(status, trailers);
                    }
                }, headers);
            }
        };
    }
}
