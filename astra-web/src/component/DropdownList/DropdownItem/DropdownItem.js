import "./DropdownItem.css";

const DropdownItem = ({item}) => {
    return (
        <li className="DropdownItem">
            {item}
        </li>
    );
}

export default DropdownItem;