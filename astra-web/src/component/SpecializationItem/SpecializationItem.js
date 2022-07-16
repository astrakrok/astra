import { Link } from "react-router-dom";
import { page } from "../../constant/page";
import Tooltipped from "../Tooltipped/Tooltipped";
import "./SpecializationItem.css";

const SpecializationItem = ({specialization}) => {
    return (
        <div className="SpecializationItem">
            <div className="edit">
                <Tooltipped tooltip="Редагувати">
                    <Link to={page.admin.specializations.id(specialization.id).edit}>
                        <i className="material-icons">create</i>
                    </Link>
                </Tooltipped>
            </div>
            <div className="content s-vflex-center">
                {specialization.title}
            </div>
        </div>
    );
}

export default SpecializationItem;
