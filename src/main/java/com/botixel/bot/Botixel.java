package com.botixel.bot;

import com.botixel.api.ApiAcess;
import com.botixel.api.ApiKeyStore;
import com.botixel.utils.Filter;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Botixel extends ListenerAdapter {
    public ApiKeyStore apiKeyStore = new ApiKeyStore();
    private final ApiAcess apiAcess = new ApiAcess(apiKeyStore);
    private final Filter filter = new Filter();

    public static void main(String[] args) {

        //API de um ‘bot’ de discord
        JDABuilder.createDefault("API Key")
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

            String apiKey = message.substring("!api".length()).trim();

            if (apiKey.isEmpty()) {
                event.getChannel().sendMessage("❗ Você precisa informar a chave API do Hypixel ❗").queue();
                return;
            } else {
                apiKeyStore.setApiKey(apiKey);
                event.getChannel().sendMessage("✅ Chave API configurada com sucesso ✅").queue();
            }
        }

        //Pesquisar um ‘item’
        if (message.startsWith("!pItem")) {

            String item = message.substring(6, message.length() - 1).trim();
            String price = message.substring(message.length() - 1).trim();

            if (item.isEmpty()) {
                event.getChannel().sendMessage("❗ Você precisa informar um item❗").queue();
            } else {
                String result = apiAcess.encontrarItem();
                String filtered = filter.lowestPrice(result, item, price);
                event.getChannel().sendMessage("```json\n" + filtered + "\n```").queue();
            }
        }
    }
}
