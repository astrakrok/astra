const DisplayBoundary = ({
    children,
    condition
}) => {
    return (
        condition ? (
            children
        ) : null
    );
}

export default DisplayBoundary;
