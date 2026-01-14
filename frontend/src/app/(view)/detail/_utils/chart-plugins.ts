import { Plugin } from 'chart.js'

/* 차트와 범례 사이의 간격 Plugin */
export const legendMarginPlugin = (
  spacing: number = 46
): Plugin<'line'> => ({
  id: 'legendMargin',
  beforeInit(chart, legend, options) {
    const fitValue = chart.legend?.fit

    chart.legend.fit = function fit() {
      fitValue.bind(chart.legend)()
      return (this.height += spacing)
    }
  },
})

/* 임의 Tooltip Plugin */
type TooltipBubbleOptions = {
  labels: string[]
  hour: number
  text: (hour: number) => string
}

export const tooltipBubblePlugin = ({
    labels,
    hour,
    text,
  }: TooltipBubbleOptions): Plugin<'line'> => ({
  id: 'tooltipBubble',
  afterDraw(chart) {
    const { ctx, scales } = chart

    const leftIndex = labels.findIndex(l => Number(l) === hour - 1)
    const rightIndex = labels.findIndex(l => Number(l) === hour + 1)
    if (leftIndex === -1 || rightIndex === -1) return

    const xLeft = scales.x.getPixelForTick(leftIndex)
    const xRight = scales.x.getPixelForTick(rightIndex)
    const x = (xLeft + xRight) / 2

    const topY = scales.y.top - 35
    const message = text(hour)

    const padding = 10
    ctx.font = '12px Pretendard'
    const textWidth = ctx.measureText(message).width

    const bubbleWidth = textWidth + padding * 2
    const bubbleHeight = 28

    ctx.save()

    ctx.fillStyle = '#3B82F6'
    ctx.beginPath()
    ctx.roundRect(
      x - bubbleWidth / 2,
      topY,
      bubbleWidth,
      bubbleHeight,
      14
    )
    ctx.fill()

    ctx.fillStyle = '#FFFFFF'
    ctx.textAlign = 'center'
    ctx.textBaseline = 'middle'
    ctx.fillText(message, x, topY + bubbleHeight / 2)

    ctx.restore()
  },
})

/* 임의 vertical line plugin */
type VerticalLineOptions = {
  labels: string[]
  hour: number
  color?: string
}

export const verticalLinePlugin = ({
    labels,
    hour,
    color = '#0C4596',
  }: VerticalLineOptions): Plugin<'line'> => ({
  id: 'verticalLine',
  afterDraw(chart) {
    const { ctx, scales, chartArea } = chart
    if (!chartArea) return

    const xScale = scales.x
    const yScale = scales.y

    const leftIndex = labels.findIndex(l => Number(l) === hour-1)
    const rightIndex = labels.findIndex(l => Number(l) === hour+1)

    if (leftIndex === -1 || rightIndex === -1) return

    const xLeft = xScale.getPixelForTick(leftIndex)
    const xRight = xScale.getPixelForTick(rightIndex)
    const x = (xLeft + xRight) / 2

    ctx.save()

    // 점선
    ctx.setLineDash([4, 4])
    ctx.strokeStyle = color ? color : '#0C4596'
    ctx.lineWidth = 1
    ctx.beginPath()
    ctx.moveTo(x, yScale.top)
    ctx.lineTo(x, yScale.bottom)
    ctx.stroke()

    // 삼각형
    const triangleSize = 6
    const triangleY = chartArea.top

    ctx.setLineDash([])
    ctx.fillStyle = color ? color : '#0C4596'
    ctx.beginPath()
    ctx.moveTo(x - triangleSize, triangleY)
    ctx.lineTo(x + triangleSize, triangleY)
    ctx.lineTo(x, triangleY + triangleSize)
    ctx.closePath()
    ctx.fill()

    ctx.restore()
  },
})