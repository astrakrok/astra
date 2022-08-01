const withAllAvailableHeight = (
    Component
) => {
    return props => (
        <>
            <Component {...props} />
            <div className="equal-flex" />
        </>
    );
}

export default withAllAvailableHeight;
