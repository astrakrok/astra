import Tooltipped from "../Tooltipped/Tooltipped";
import "./ExamItem.css";

const ExamItem = ({
    exam,
    onUpdateClick = () => {},
    onDeleteClick = () => {}
}) => {
    return (
        <div className="ExamItem s-hflex">
            <div className="wrapper full-width s-hflex">
                <div className="title equal-flex s-vflex-center center">
                    {exam.title}
                </div>
                <Tooltipped className="edit s-vflex-center" tooltip="Редагувати" position="top" onClick={onUpdateClick}>
                    <i className="material-icons">create</i>
                </Tooltipped>
                <Tooltipped className="delete s-vflex-center" tooltip="Видалити" position="top" onClick={onDeleteClick}>
                    <i className="material-icons">close</i>
                </Tooltipped>
            </div>
        </div>
    );
}

export default ExamItem;
