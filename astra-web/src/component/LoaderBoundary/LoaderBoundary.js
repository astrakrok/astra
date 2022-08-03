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
            <div {...otherProps}>
                <Preloader size={size} {...otherProps} />
            </div>
        ) : (
            children
        )
    );
}

export default LoaderBoundary;
