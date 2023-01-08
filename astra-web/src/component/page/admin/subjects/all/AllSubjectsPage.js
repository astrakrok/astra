import {getSubjectsDetailsPage} from "../../../../../service/subject.service";
import PopupConsumer from "../../../../../context/popup/PopupConsumer";
import SubjectListItem from "../../../../SubjectListItem/SubjectListItem";
import Spacer from "../../../../Spacer/Spacer";
import SubjectForm from "../../../../form/SubjectForm/SubjectForm";
import "./AllSubjectsPage.css";
import withTitle from "../../../../hoc/withTitle/withTitle";
import {defaultSubject} from "../../../../../data/default/subject";
import Paginated from "../../../../Paginated/Paginated";
import SubjectFilter from "../../../../filter/SubjectFilter/SubjectFilter";

const AllSubjectsPage = () => {
    const fetchSubjectsPage = async (filter, pageable) => {
        return await getSubjectsDetailsPage(filter, pageable);
    }

    const onSubjectSaved = (setPopupState, refreshPage) => {
        setPopupState();
        refreshPage();
    }

    const subjectForm = (setPopupState, subject, refreshPage) => {
        return (
            <SubjectForm onSuccess={() => onSubjectSaved(setPopupState, refreshPage)} subject={subject} />
        );
    }

    const openPopup = (setPopupState, subject = defaultSubject, refreshPage = () => {}) => {
        setPopupState({
            bodyGetter: () => subjectForm(setPopupState, subject, refreshPage)
        });
    }

    const renderSubjectItem = (item, refreshPage) => (
        <PopupConsumer key={item.id}>
            {
                ({setPopupState}) => (
                    <SubjectListItem subject={item} onUpdateClick={() => openPopup(setPopupState, item, refreshPage)} />
                )
            }
        </PopupConsumer>
    )

    return (
        <div className="AllSubjectsPage container">
            <div className="row">
                {/* <div className="s-hflex-end">
                    <PopupConsumer>
                        {
                            ({setPopupState}) => (
                                <Button isFilled={true} onClick={() => openPopup(setPopupState)}>
                                    Створити
                                </Button>
                            )
                        }
                    </PopupConsumer>
                </div> */}
                <Spacer height={15}/>
                <Paginated pageSize={10} pageHandler={fetchSubjectsPage}>
                    {({
                        filter: ({setFilter}) => <SubjectFilter onFilterSelected={setFilter} />,
                        content: ({items, refreshPage}) => {
                            return items.map(item => renderSubjectItem(item, refreshPage))
                        }
                    })}
                </Paginated>
            </div>
        </div>
    );
}

export default withTitle(AllSubjectsPage, "Предмети");
