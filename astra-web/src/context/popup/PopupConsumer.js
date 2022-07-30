import PopupContext from "./PopupContext";

const PopupConsumer = ({children}) => {
    return (
        <PopupContext.Consumer>
            {context => {
                if (context === undefined) {
                    throw new Error('PopupConsumer must be used within a PopupProvider')
                }
                return children(context)
            }}
        </PopupContext.Consumer>
    );
}

export default PopupConsumer;
