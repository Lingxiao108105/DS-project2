package edu.javafx.component;

import edu.ThreadPool.ClientThreadPool;
import edu.common.exception.NotInitException;
import edu.config.ClientConfig;
import edu.dto.ChatGetRequest;
import edu.dto.ChatGetResponse;
import edu.dto.ChatMessage;
import edu.javafx.basic.TextAreaTableCell;
import edu.javafx.component.Impl.DrawLineButtonComponent;
import edu.rpc.RpcClient;
import edu.service.ChatService;
import edu.service.SendChatMessageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DefaultStringConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ChatComponent {

    private static ChatComponent chatComponent = null;

    private final TextArea chatTextArea;
    private ScheduledFuture<?> updateChatTask;

    private ObservableList<ChatMessage> chatMessageList;

    public static void init(TableView<ChatMessage> ChatTable,
                            TableColumn<ChatMessage, String> nameColumn,
                            TableColumn<ChatMessage, String> messageColumn,
                            TextArea chatTextArea,
                            Button sendButton) {
        chatComponent = new ChatComponent(ChatTable,nameColumn,messageColumn,chatTextArea,sendButton);
    }

    public static ChatComponent getInstance(){
        if(chatComponent == null){
            throw new NotInitException(DrawLineButtonComponent.class.getName());
        }
        return chatComponent;
    }

    private ChatComponent(TableView<ChatMessage> ChatTable,
                          TableColumn<ChatMessage, String> nameColumn,
                          TableColumn<ChatMessage, String> messageColumn,
                          TextArea chatTextArea,
                          Button sendButton) {
        ChatTable.setPlaceholder(
                new Label(""));

        nameColumn.setCellValueFactory(new PropertyValueFactory<ChatMessage, String>("name"));
        nameColumn.setCellFactory(TextAreaTableCell.forTableColumn(new DefaultStringConverter()));

        messageColumn.setCellValueFactory(new PropertyValueFactory<ChatMessage, String>("message"));
        messageColumn.setCellFactory(TextAreaTableCell.forTableColumn(new DefaultStringConverter()));

        this.chatTextArea = chatTextArea;

        sendButton.setOnMouseClicked(this::sendButtonSetOnMouseClicked);

        //show chat message
        chatMessageList = FXCollections.observableArrayList();
        ChatTable.setItems(chatMessageList);

        //get message from server
        this.updateChatTask = ClientThreadPool.getInstance().getScheduledExecutorService().scheduleWithFixedDelay(
                this::getChatMessages,
                50,
                50,
                TimeUnit.MILLISECONDS
        );
    }

    private void sendButtonSetOnMouseClicked(MouseEvent e){
        String text = this.chatTextArea.getText();
        ChatMessage chatMessage = new ChatMessage(ClientConfig.clientInfo.getName(), text);
        SendChatMessageService.sendChatMessage(chatMessage);
    }

    private void getChatMessages(){
        ChatService chatService = RpcClient.getInstance().getChatService();
        ChatGetRequest chatGetRequest = new ChatGetRequest(this.chatMessageList.size());
        ChatGetResponse chatGetResponse = chatService.getChatMessage(ClientConfig.clientInfo, chatGetRequest);
        if(chatGetResponse.getChatMessageList().size() > 0){
            System.out.println(Arrays.toString(chatGetResponse.getChatMessageList().toArray()));
        }
        this.chatMessageList.addAll(chatGetResponse.getChatMessageList());
    }

}
