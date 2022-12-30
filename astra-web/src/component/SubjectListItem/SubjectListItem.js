import Badge from "../Badge/Badge";
import Tooltipped from "../Tooltipped/Tooltipped";
import Spacer from "../Spacer/Spacer";
import Divider from "../Divider/Divider";
import "./SubjectListItem.css";

const SubjectListItem = ({
    subject,
    onUpdateClick = () => {}
}) => {
    return (
        <div className="full-width SubjectListItem">
            <div className="s-hflex">
                <div className="s-vflex info equal-flex">
                    <div className="s-hflex-start title">
                        {subject.title}
                    </div>
                    <Spacer height={5}/>
                    <div className="s-hflex s-flex-wrapped">
                        <Badge>{subject.specialization.step.title} | {subject.specialization.title}</Badge>
                    </div>
                </div>
                <Divider orientation="none"/>
                <Spacer width={10}/>
                <div className="s-vflex-center options">
                    <Tooltipped tooltip="Редагувати" onClick={onUpdateClick}>
                        <i className="material-icons clickable">create</i>
                    </Tooltipped>
                </div>
            </div>
        </div>
    );
}

export default SubjectListItem;
