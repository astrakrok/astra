import Button from "../../../../Button/Button";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";
import AdminsList from "../../../../AdminsList/AdminsList";
import "./AllAdminsPage.css";
import AddAdminForm from "../../../../form/AddAdminForm/AddAdminForm";

const admins = [
    {
        id: 1,
        email: "test.email@gmail.com",
        name: "Test",
        surname: "Testovich"
    },
    {
        id: 2,
        email: "valid.email@gmail.com",
        name: "Ivan",
        surname: "Ivanovich"
    },
    {
        id: 3,
        email: "email@ukr.net",
        name: "Andrii",
        surname: "Bosyk"
    },
    {
        id: 4,
        email: "admin@gmail.com",
        name: "Admin",
        surname: "Admin"
    },
];

const AllAdminsPage = () => {
    const openAdminForm = setPopupState => {
        setPopupState({
            bodyGetter: () => <AddAdminForm/>
        });
    }

    const deleteAdmin = id => {
        console.log("Admin's id:", id);
    }

    return (
        <div className="container">
            <div className="row">
                <div className="s-vflex">
                    <div className="s-hflex-end">
                        <PopupConsumer>
                            {
                                ({setPopupState}) => (
                                    <Button isFilled={true} onClick={() => openAdminForm(setPopupState)}>
                                        Створити
                                    </Button>
                                )
                            }
                        </PopupConsumer>
                    </div>
                    <AdminsList admins={admins} onDeleteConfirm={deleteAdmin}/>
                </div>
            </div>
        </div>
    );
}

export default AllAdminsPage;
