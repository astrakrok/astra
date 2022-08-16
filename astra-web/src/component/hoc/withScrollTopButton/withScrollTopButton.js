import ScrollTopButton from "./ScrollTopButton/ScrollTopButton";
import "./withScrollTopButton.css";

const withScrollTopButton = (
    Component
) => {
    return props => {
        return (
            <>
                <Component {...props} />
                <ScrollTopButton/>
            </>
        );
    };
}

export default withScrollTopButton;
