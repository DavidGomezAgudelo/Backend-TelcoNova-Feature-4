package com.Fabrica.TelcoNova.config;

import com.Fabrica.TelcoNova.model.UserModel;
import com.Fabrica.TelcoNova.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class CustomGraphQLInterceptor implements WebGraphQlInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {

            String email = authentication.getName();

            Optional<UserModel> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                UserModel user = userOpt.get();

                // Configurar el contexto con el usuario
                request.configureExecutionInput((executionInput, builder) -> {
                    return builder.graphQLContext(contextBuilder ->
                            contextBuilder.put("user", user)
                    ).build();
                });
            } 

        } 

        return chain.next(request);
    }
}
