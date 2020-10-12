import MTabbar from './packages/MTabbar/index.js'
import MHeader from './packages/MHeader/index.js'
import MFloor from './packages/MFloor/index.js'
import MGoodbox from './packages/MGoodbox/index.js'
import MCategorybox from './packages/MCategorybox/index.js'
import MLoginbar from './packages/MLoginbar/index.js'
import MButton from './packages/MButton/index.js'

const version = '1.0.0'

const install = function (Vue, config = {}) {
    if (install.installed) return
    Vue.component(MTabbar.name, MTabbar)
    Vue.component(MHeader.name, MHeader)
    Vue.component(MFloor.name, MFloor)
    Vue.component(MGoodbox.name, MGoodbox)
    Vue.component(MCategorybox.name, MCategorybox)
    Vue.component(MLoginbar.name, MLoginbar)
    Vue.component(MButton.name, MButton)
};

export default {
    version,
    install
}