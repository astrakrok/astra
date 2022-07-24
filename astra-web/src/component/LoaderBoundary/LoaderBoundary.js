import Preloader from "../Preloader/Preloader";

const LoaderBoundary = props => {
    const {
        children,
        condition,
        size = "big",
        ...otherProps
    } = props;
    return (
        condition ? (
            <Preloader size={size} {...otherProps} />
        ) : (
            children
        )
    );
}

export default LoaderBoundary;
