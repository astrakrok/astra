import React, {useEffect, useState} from "react";
import "./TabPanel.css";

const TabPanel = ({
    tabs,
    type = "primary",
    selectedTab = 0,
    stretch = false
}) => {
    const [selected, setSelected] = useState(selectedTab);

    useEffect(() => {
        setSelected(selectedTab);
    }, [selectedTab]);

    const handleTabClick = (tab, index) => {
        if (tab.disabled) {
            return;
        }
        if (!tab.passive) {
            setSelected(index);
        }
        if (tab.onClick) {
            tab.onClick(index);
        }
    }

    const renderTab = (tab, index) => (
        <div
            className={`s-vflex-center ${stretch ? "equal-flex" : ""} center panel-tab ${index === selected ? "selected" : ""} ${tab.className ? tab.className : ""} ${tab.disabled ? "disabled" : ""}`}
            onClick={() => handleTabClick(tab, index)}
        >
            {tab.title}
        </div>
    );

    return (
        <div className={`TabPanel s-vflex ${type}`}>
            <div className="panel-tabs s-hflex">
                <div className="spacer" />
                <div className="full-width s-hflex">
                    {
                        tabs.map((tab, index) => {
                            return (
                                <React.Fragment key={index}>
                                    {renderTab(tab, index)}
                                    <div className="spacer" />
                                </React.Fragment>
                            );
                        })
                    }
                </div>
            </div>
        </div>
    );
}

export default TabPanel;
