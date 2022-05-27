package edu.javafx.component;

import edu.common.exception.NotInitException;
import edu.config.ClientConfig;
import edu.dto.ChatMessage;
import edu.javafx.ExceptionMessageGUIController;
import edu.javafx.basic.TextAreaTableCell;
import edu.service.Impl.ServerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DefaultStringConverter;

import java.util.List;

public class ChatComponent {

    private static ChatComponent chatComponent = null;

    private final TextArea chatTextArea;

    private final ObservableList<ChatMessage> chatMessageList;

    public static void init(TableView<ChatMessage> ChatTable,
                            TableColumn<ChatMessage, String> nameColumn,
                            TableColumn<ChatMessage, String> messageColumn,
                            TextArea chatTextArea,
                            Button sendButton) {
        chatComponent = new ChatComponent(ChatTable,nameColumn,messageColumn,chatTextArea,sendButton);
    }

    public static ChatComponent getInstance(){
        if(chatComponent == null){
            throw new NotInitException(ChatComponent.class.getName());
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

        //get all previous chat message(not active)
        //getChatMessages();
    }

    private void sendButtonSetOnMouseClicked(MouseEvent e){
        String text = this.chatTextArea.getText();
        //clean the text
        this.chatTextArea.setText("");
        if(text.isBlank()){
            new ExceptionMessageGUIController("Please send non-blank chat!");
            return;
        }
        ChatMessage chatMessage = new ChatMessage(ClientConfig.clientInfo.getName(), text);
        ServerService.sendChatMessage(chatMessage);
    }

    public ObservableList<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }

    /**
     * get all chat message from server, please use at init if you want
     */
    private void getChatMessages(){
        List<ChatMessage> chat = ServerService.getChat();
        if(chat == null){
            return;
        }
        this.chatMessageList.clear();
        this.chatMessageList.addAll(chat);
    }

}
