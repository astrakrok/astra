import "./TestingNavigation.css";

const TestingNavigation = ({
    items,
    onSelect = () => {}
}) => {
    const renderNavigationItem = (item, index) => {
        const order = index + 1;
        const className = "item clickable" +
            (item.selected ? " selected" : "") + " " +
            item.status;

        return (
            <div className="wrapper" key={index}>
                <div className={className} onClick={() => onSelect(index)}>
                    {order} {item.status === "failed" ? "!" : ""}
                </div>
            </div>
        );
    }

    return (
        <div className="TestingNavigation s-hflex wrap-flex">
            {
                items.map(renderNavigationItem)
            }
        </div>
    );
}

export default TestingNavigation;
