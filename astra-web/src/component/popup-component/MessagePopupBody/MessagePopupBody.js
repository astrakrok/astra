import "./MessagePopupBody.css";

const MessagePopupBody = ({message}) => {
    return (
        <div className="MessagePopupBody s-vflex">
            <div className="s-hflex-center message">
                {message}
            </div>
        </div>
    );
}

export default MessagePopupBody;
