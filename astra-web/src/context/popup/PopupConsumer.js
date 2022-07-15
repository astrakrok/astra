import PopupContext from "./PopupContext";

const PopupConsumer = ({children}) => {
    return (
        <PopupContext.Consumer>
            {context => {
                if (context === undefined) {
                    throw new Error('CountConsumer must be used within a CountProvider')
                }
                return children(context)
            }}
        </PopupContext.Consumer>
    );
}

export default PopupConsumer;
