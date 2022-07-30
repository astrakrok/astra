import Button from "../../Button/Button";
import Spacer from "../../Spacer/Spacer";

const LinkFooter = ({
    title = "",
    to = "",
    setPopupState
}) => {
    return (
        <>
            <Spacer height={20} />
            <div className="s-hflex-center">
                <Button to={to} isFilled={true} onClick={() => setPopupState()}>
                    {title}
                </Button>
            </div>
        </>
    );
}

export default LinkFooter;
