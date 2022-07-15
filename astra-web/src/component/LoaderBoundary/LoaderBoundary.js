import Preloader from "../Preloader/Preloader";

const LoaderBoundary = props => {
    const {
        children,
        condition,
        ...otherProps
    } = props;
    return (
        condition ? (
            <Preloader {...otherProps} />
        ) : (
            children
        )
    );
}

export default LoaderBoundary;
