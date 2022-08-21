import {message} from "../../constant/message";
import PopupConsumer from "../../context/popup/PopupConsumer";
import ActionDialog from "../popup-component/ActionDialog/ActionDialog";
import Table from "../Table/Table";
import "./AdminsList.css";

const AdminsList = ({
                        admins,
                        onDeleteConfirm = () => {
                        }
                    }) => {
    const askToDelete = (id, setPopupState) => {
        setPopupState({
            bodyGetter: () => <ActionDialog
                message={message.askForAdminDeletion}
                setPopupState={setPopupState}
                onConfirm={() => onDeleteConfirm(id)}/>
        });
    }

    const renderAdminItem = (admin, index) => (
        <tr key={admin.id}>
            <td>{index + 1}</td>
            <td>{admin.email}</td>
            <td className="center">{admin.surname} {admin.name}</td>
            <td className="min-width center">
                <div className="delete">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <i
                                    className="material-icons weight-strong clickable"
                                    onClick={() => askToDelete(admin.id, setPopupState)}
                                >
                                    close
                                </i>
                            )
                        }
                    </PopupConsumer>
                </div>
            </td>
        </tr>
    );

    return (
        <Table className="AdminsList">
            <thead>
            <tr>
                <th>#</th>
                <th>E-mail</th>
                <th>Прізвище та ім'я</th>
                <th>Видалити</th>
            </tr>
            </thead>
            <tbody>
            {
                admins.map(renderAdminItem)
            }
            </tbody>
        </Table>
    );
}

export default AdminsList;
