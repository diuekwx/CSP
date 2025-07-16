import CalendarHeatmap from 'react-calendar-heatmap';

export default function HomePage() {
    
    const fetchData =  async () => {
        const response = await fetch('localhost:5050/api/contribution/dates');
        if (!response.ok){
            throw new Error(`httperror ${response.status}`);
        }
        const data = response.json();

    }
    
    const values = null;


    return (
        <>
        Hello
        <div> 
            <CalendarHeatmap
            startDate={new Date('2016-01-01')}
            endDate={new Date('2016-04-01')}
            values={[
                { date: '2016-01-01', count: 12 },
                { date: '2016-01-22', count: 122 },
                { date: '2016-01-30', count: 38 },
                
            ]}
            />
        </div>

        </>
    )
}