import { Link } from "react-router-dom";
import { page } from "../../constant/page";
import ItemBlock from "../ItemBlock/ItemBlock";
import Tooltipped from "../Tooltipped/Tooltipped";
import "./SpecializationItem.css";

const SpecializationItem = ({specialization}) => {
    return (
        <div className="SpecializationItem">
            <ItemBlock>
                <div className="edit">
                     <Tooltipped tooltip="Редагувати">
                         <Link to={page.admin.specializations.id(specialization.id).edit}>
                             <i className="material-icons">create</i>
                         </Link>
                     </Tooltipped>
                 </div>
                 <div className="title">
                     {specialization.title}
                 </div>
            </ItemBlock>
        </div>
    );
}

export default SpecializationItem;
