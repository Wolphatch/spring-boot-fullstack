import { Button } from '@chakra-ui/react'
import SideBarWithHeader from './shared/SideBar'


const App = ()=>{
    return (
      <SideBarWithHeader>
        <Button colorScheme="teal" variant='outline'>Click Me</Button>
      </SideBarWithHeader>
    )
}

export default App;