plugins {
    id("messages.common-conventions")
    id("messages.publication")
}

afterEvaluate {
    collector.JavadocAggregator.addProject(this)
}
