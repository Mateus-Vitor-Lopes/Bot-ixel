package com.botixel.bot;

import com.botixel.api.ApiAcess;
import com.botixel.api.ApiKeyStore;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Botixel extends ListenerAdapter {
    private final ApiKeyStore ApiKeyStore = new ApiKeyStore();
    private final ApiAcess ApiAcess = new ApiAcess(ApiKeyStore);

    public static void main(String[] args) {

        //API de um ‘bot’ de discord
        JDABuilder.createDefault("API AQUI")
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new Botixel())
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String message = event.getMessage().getContentRaw();

        //Cadastrar uma chave API
        if (message.toLowerCase().startsWith("!api")) {

            String ApiKey = message.substring("!api".length()).trim();

            if (ApiKey.isEmpty()) {
                event.getChannel().sendMessage("❗ Você precisa informar a chave API do Hypixel ❗").queue();
                return;
            } else {
                ApiKeyStore.setApiKey(ApiKey);
                event.getChannel().sendMessage("✅ Chave API configurada com sucesso ✅").queue();
            }
        }

        //Pesquisar um ‘item’
        if (message.startsWith("!pItem")) {

            String item = message.substring("!pItem".length()).trim();

            if (item.isEmpty()) {
                event.getChannel().sendMessage("❗ Você precisa informar um item❗").queue();
                return;
            } else if (item.contains("<")) {
                event.getChannel().sendMessage("<<<<<<<<<<<<<<<").queue();
            } else {

            }
        }
    }
}
