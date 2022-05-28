package edu.javafx.component;

import edu.common.enums.Role;
import edu.common.exception.NotInitException;
import edu.config.ClientConfig;
import edu.dto.ClientInfo;
import edu.javafx.basic.TextAreaTableCell;
import edu.service.Impl.ServerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.DefaultStringConverter;

import java.util.List;

/**
 * AcceptedUserComponent controls the userTable of canvas
 * shows users who are currently editing the same whiteboard
 * control kick button
 * @author lingxiao
 */
public class AcceptedUserComponent {

    private static AcceptedUserComponent acceptedUserComponent = null;

    private final ObservableList<ClientInfo> clientNameObservableList;

    private final TableView<ClientInfo> userTable;

    public static void init(TableView<ClientInfo> userTable,
                            TableColumn<ClientInfo, String> userColumn,
                            Button kickButton) {
        acceptedUserComponent = new AcceptedUserComponent(userTable,userColumn,kickButton);
    }

    public static AcceptedUserComponent getInstance(){
        if(acceptedUserComponent == null){
            throw new NotInitException(AcceptedUserComponent.class.getName());
        }
        return acceptedUserComponent;
    }

    private AcceptedUserComponent(TableView<ClientInfo> userTable,
                                  TableColumn<ClientInfo, String> userColumn,
                                  Button kickButton) {
        this.userTable = userTable;

        userColumn.setCellValueFactory(new PropertyValueFactory<ClientInfo, String>("name"));
        userColumn.setCellFactory(TextAreaTableCell.forTableColumn(new DefaultStringConverter()));

        //client cannot kick anyone
        if(ClientConfig.role == Role.MANAGER){
            kickButton.setOnMouseClicked(this::kickButtonSetOnMouseClicked);
        }else if(ClientConfig.role == Role.CLIENT){
            kickButton.setVisible(false);
        }

        //show chat message
        this.clientNameObservableList = FXCollections.observableArrayList();
        userTable.setItems(clientNameObservableList);

        getAcceptedClients();

    }

    private void kickButtonSetOnMouseClicked(MouseEvent e){
        ClientInfo clientInfo = this.userTable.getSelectionModel().getSelectedItem();
        ServerService.kickClient(clientInfo);
    }

    public ObservableList<ClientInfo> getClientNameObservableList() {
        return clientNameObservableList;
    }

    /**
     * get all accepted client from server
     */
    private void getAcceptedClients(){
        List<ClientInfo> acceptedClient = ServerService.getAcceptedClient();
        if(acceptedClient != null){
            clientNameObservableList.addAll(acceptedClient);
        }

    }


}
