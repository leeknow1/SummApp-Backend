package com.leeknow.summapp.service;

import com.leeknow.summapp.entity.Message;
import com.leeknow.summapp.enums.Language;
import com.leeknow.summapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageService {

    private static MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        MessageService.messageRepository = messageRepository;
    }

    public static String getMessage(Language language, Integer messageIds) {
        Optional<Message> message = messageRepository.findById(messageIds);

        if (message.isEmpty())
            return "";

        return Language.getLanguageString(message.get().getNameRu(), message.get().getNameKz(), language);
    }

    public static Map<Integer, String> getMessageMap(Language language, Integer... messageIds) {
        Map<Integer, String> mapMessages = new HashMap<>();
        List<Message> messages = messageRepository.findAllByMessageIdIn(List.of(messageIds));

        for (Message message : messages) {
            mapMessages.put(message.getMessageId(), Language.getLanguageString(message.getNameRu(), message.getNameKz(), language));
        }

        return mapMessages;
    }
}
