import {Link} from "react-router-dom";
import {page} from "../../constant/page";
import Badge from "../Badge/Badge";
import Tooltipped from "../Tooltipped/Tooltipped";
import Spacer from "../Spacer/Spacer";
import Divider from "../Divider/Divider";
import "./SubjectListItem.css";

const SubjectListItem = ({subject}) => {
    return (
        <div className="full-width SubjectListItem">
            <div className="s-hflex">
                <div className="s-vflex info equal-flex">
                    <div className="s-hflex-start title">
                        {subject.title}
                    </div>
                    <Spacer height={5}/>
                    <div className="s-hflex s-flex-wrapped">
                        {
                            subject.specializations.map(item => <Badge key={item.id}>{item.title}</Badge>)
                        }
                    </div>
                </div>
                <Divider orientation="none"/>
                <Spacer width={10}/>
                <div className="s-vflex-center options">
                    <Tooltipped tooltip="Редагувати">
                        <Link to={page.admin.subjects.id(subject.id).edit}>
                            <i className="material-icons">create</i>
                        </Link>
                    </Tooltipped>
                </div>
            </div>
        </div>
    );
}

export default SubjectListItem;