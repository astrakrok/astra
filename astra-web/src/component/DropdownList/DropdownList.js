import DropdownItem from "./DropdownItem/DropdownItem";
import "./DropdownList.css";

const DropdownList = ({items}) => {
    return (
        <ul className="DropdownList">
            {
                items.map((item, index) => <DropdownItem key={index} item={item} />)
            }
        </ul>
    );
}

export default DropdownList;
