import parse from "html-react-parser";
import Spacer from "../Spacer/Spacer";
import "./InfoBlock.css";

const InfoBlock = ({
                       children
                   }) => {
    return (
        <div className="InfoBlock">
            <div className="s-hflex">
                <div className="info-icon s-vflex-center">
                    <i className="material-icons medium">info_outline</i>
                </div>
                <Spacer width={10}/>
                <div className="info s-vflex-center">
                    {typeof children === "string" ? parse(children) : children}
                </div>
            </div>
        </div>
    );
}

export default InfoBlock;
