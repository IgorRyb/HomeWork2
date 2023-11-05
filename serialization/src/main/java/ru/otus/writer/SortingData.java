package ru.otus.writer;

import ru.otus.domen.ChatSession;
import ru.otus.domen.Member;
import ru.otus.domen.Message;
import ru.otus.domen.MessagesReport;
import ru.otus.dto.InfoDto;
import ru.otus.dto.ResponseDto;

import java.util.*;

public class SortingData {

    Map<String, List<InfoDto>> groupedData;

    public SortingData() {
        this.groupedData = new HashMap<>();
    }

    private void sortInfoByDate(List<InfoDto> infoDtoList) {
        infoDtoList.sort(Comparator.comparing(InfoDto::getSendDate));
    }

    private String getLastMember(ChatSession chatSession, Message message) {
        return chatSession.getMembers().stream()
                .filter(member -> member.getHandleId() == message.getHandleId())
                .map(Member::getLast)
                .findFirst().orElse(null);
    }

    public List<ResponseDto> compileData(MessagesReport messagesReport) {
        Map<String, List<InfoDto>> compileData = getData(messagesReport);
        List<ResponseDto> dtoList = groupedData.entrySet().stream()
                .map(stringListEntry -> new ResponseDto(stringListEntry.getKey(), stringListEntry.getValue()))
                .toList();
        dtoList.forEach(responseDto -> sortInfoByDate(responseDto.getNestedInfo()));
        return dtoList;
    }

    public Map<String, List<InfoDto>> getData(MessagesReport messagesReport) {
        for (ChatSession chatSession : messagesReport.getChatSessions()) {
            for (Message message : chatSession.getMessages()) {
                String number = message.getBelongNumber();
                InfoDto infoDto = new InfoDto(chatSession.getChatIdentifier(),
                        getLastMember(chatSession, message), message.getSendDate(), message.getText());
                groupedData.merge(number, new ArrayList<>(Arrays.asList(infoDto)), (l1, l2) -> {
                    List<InfoDto> resultList = new ArrayList<>(l1);
                    resultList.addAll(l2);
                    return resultList;
                });
            }
        }
        return groupedData;
    }
}
